package restaurant.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import restaurant.domain.Staffs

@Repository
interface StaffRepository: JpaRepository<Staffs, Long>, JpaSpecificationExecutor<Staffs> {

  override fun findAll(pageable: Pageable): Page<Staffs>

}

