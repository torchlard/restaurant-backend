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
  @Query("insert into master_order('checkinDt','table_id') values(?2,?1)", nativeQuery = true)
  fun checkin(tableId: Long, checkinDt: String)

  @Transactional @Modifying
  @Query("update master_order set checkout_dt=?1, status='Completed' where id = (" +
      "select master_order_id from table where tableNo = ?2)) ", nativeQuery = true)
  fun checkout(checkoutDt: LocalDateTime = LocalDateTime.now(), tableId: Long)

//  @Transactional @Modifying
//  @Query("update MasterOrders m set m.checkinDt=?1, m.status='Serving' where m.id=?2")
//  fun checkin(checkinDt: LocalDateTime, id: Long)


}
