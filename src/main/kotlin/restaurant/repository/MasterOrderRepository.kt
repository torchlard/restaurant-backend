package restaurant.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restaurant.domain.MasterOrders
import restaurant.domain.Status
import restaurant.utils.DateTimeHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.transaction.Transactional

@Repository
interface MasterOrderRepository: JpaRepository<MasterOrders, Long>, JpaSpecificationExecutor<MasterOrders> {

  override fun findAll(pageable: Pageable): Page<MasterOrders>

  @Transactional @Modifying
  @Query("insert into master_orders(checkin_dt) values(?1)", nativeQuery = true)
  fun createRecord(checkinDt: String): Int

  @Transactional @Modifying
  @Query("update master_orders set checkout_dt=?1, status='Completed' where id = (" +
      "select master_order_id from table where table_no = ?2)) ", nativeQuery = true)
  fun checkout(checkoutDt: String = DateTimeHelper.getCurrentSQLDt(), tableId: Long)

  @Transactional @Modifying
  @Query("alter table master_orders auto_increment=1", nativeQuery = true)
  fun resetAutoIncrement()

}
