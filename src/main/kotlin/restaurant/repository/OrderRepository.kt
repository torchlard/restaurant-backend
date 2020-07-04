package restaurant.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restaurant.domain.Order2
import restaurant.domain.Orders
import javax.transaction.Transactional

@Repository
interface OrderRepository: JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {

  @Query("select arrived_qty from orders where master_order_id=?1 and food_id=?2", nativeQuery = true)
  fun findByNo(masterOrderId: Long, foodId: Long): Long?

  @Transactional @Modifying
  @Query("insert into orders(ordered_qty,arrived_qty,master_order_id,food_id) values (?1,0,?2,?3)", nativeQuery = true)
  fun createOrder(orderedQty: Int, masterOrderId: Long, foodId: Long)

  @Transactional @Modifying
  @Query("update orders set o.arrived_qty = ?1 where food_id=?2 and master_order_id=?3", nativeQuery=true)
  fun updateArriveQuantity(arrivedQty: Int, foodId: Long, masterOrderId: Long)

  @Transactional @Modifying
  @Query("update orders set o.ordered_qty = ?1 where food_id=?2 and master_order_id=?3", nativeQuery=true)
  fun updateOrderQuantity(orderedQty: Int, foodId: Long, masterOrderId: Long)

  @Query("select ordered_qty,arrived_qty from orders where master_order_id=?1 and food_id = ?2", nativeQuery = true)
  fun selectOrder(masterOrderId: Long, foodId: Long): Order2

  @Transactional @Modifying
  @Query("delete from orders where master_order_id=?1 and food_id = ?2", nativeQuery = true)
  fun deleteOrder(masterOrderId: Long, foodId: Long)

  @Transactional @Modifying
  @Query("delete from orders where master_order_id=?1 and food_id in (?2)", nativeQuery = true)
  fun deleteOrdersInBatch(masterOrderId: Long, foodIds: String)

  @Transactional @Modifying
  @Query("alter table orders auto_increment=1", nativeQuery = true)
  fun resetAutoIncrement()


}