package restaurant.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restaurant.domain.Staffs
import javax.transaction.Transactional

@Repository
interface StaffRepository: JpaRepository<Staffs, Long>, JpaSpecificationExecutor<Staffs> {

  override fun findAll(pageable: Pageable): Page<Staffs>

  @Transactional @Modifying
  @Query("alter table staffs auto_increment=1", nativeQuery = true)
  fun resetAutoIncrement()

}

