package restaurant.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restaurant.domain.Foods
import javax.transaction.Transactional


@Repository
interface FoodRepository: JpaRepository<Foods, Long>, JpaSpecificationExecutor<Foods> {

  @Transactional @Modifying
  @Query("update foods set quantity = quantity- where id=?1", nativeQuery = true)
  fun reduceQty(qty: Int, id: Long)

  @Query("select distinct f.category from Foods f")
  fun findDistinctCategory()



}





