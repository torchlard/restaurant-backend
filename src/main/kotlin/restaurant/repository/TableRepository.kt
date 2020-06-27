package restaurant.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restaurant.domain.MasterOrders
import restaurant.domain.Tables

@Repository
interface TableRepository: JpaRepository<Tables,Long>, JpaSpecificationExecutor<Tables> {

  @Query("select master_order_id from tables where tableNo = ?1", nativeQuery = true)
  fun findMasterOrderByTableNo(tableNo: String): Long?

}


