package restaurant.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restaurant.domain.Category
import restaurant.domain.Foods
import restaurant.domain.QtyDTO
import javax.transaction.Transactional


@Repository
interface FoodRepository: JpaRepository<Foods, Long>, JpaSpecificationExecutor<Foods> {

  @Transactional @Modifying
  @Query("update foods set quantity = quantity+?1 where id=?2", nativeQuery = true)
  fun changeQty(qty: Int, id: Long)

  @Query("select distinct f.category from Foods f")
  fun findDistinctCategory(): List<Category>

  @Transactional @Modifying
  @Query("update Foods f set f.valid = false where f.id = ?1")
  fun deleteFood(foodId: Long)

  @Query("select id, quantity from food f where id in (?1)", nativeQuery = true)
  fun selectQtyInBatch(ids: String): List<QtyDTO>

  @Transactional @Modifying
  @Query("alter table foods auto_increment=1", nativeQuery = true)
  fun resetAutoIncrement()

}






