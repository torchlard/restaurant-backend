package restaurant.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional
import restaurant.domain.MasterOrders
import restaurant.domain.Tables

@Repository
interface TableRepository: JpaRepository<Tables,Long>, JpaSpecificationExecutor<Tables> {

  @Query("select master_order_id from tables where id = ?1", nativeQuery = true)
  fun findMasterOrderByTableNo(tableId: Long): Long?

  @Transactional @Modifying
  @Query("update tables set master_order_id = ?2 where id = ?1", nativeQuery = true)
  fun assignMasterId(tableId: Long, masterOrderId: Long?)

  @Transactional @Modifying
  @Query("alter table tables auto_increment=1", nativeQuery = true)
  fun resetAutoIncrement()


}


