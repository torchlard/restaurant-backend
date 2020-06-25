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
  @Query("update MasterOrders m set m.checkoutDt=?1, m.status='Completed' where m.id=?2")
  fun checkout(checkoutDt: LocalDateTime, id: Long)

//  @Transactional @Modifying
//  @Query("update MasterOrders m set m.checkinDt=?1, m.status='Serving' where m.id=?2")
//  fun checkin(checkinDt: LocalDateTime, id: Long)


}
