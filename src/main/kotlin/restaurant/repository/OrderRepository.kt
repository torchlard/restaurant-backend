package restaurant.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restaurant.domain.Orders

@Repository
interface OrderRepository: JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {

  @Query("insert into order(ordered_qty,arrived_qty,master_order_id,food_id) values (?1,0,?2,?3)", nativeQuery = true)
  fun createOrder(orderedQty: Int, masterOrderId: Long, foodId: Long)

  @Query("update Orders o set o.arrivedQty = ?1 where o.id = ?2")
  fun updateArriveQuantity(arrivedQty: Int, foodId: Long)

  @Query("update Orders o set o.orderedQty = ?1 where o.id = ?2")
  fun updateOrderQuantity(orderedQty: Int, foodId: Long)


}