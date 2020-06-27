package restaurant.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import restaurant.domain.MasterOrders
import restaurant.domain.ResponseDto
import restaurant.repository.MasterOrderRepository
import restaurant.repository.TableRepository
import restaurant.utils.DateTimeHelper
import java.lang.Exception
import java.lang.RuntimeException
import java.time.LocalDateTime

@Service
class MasterOrderService(val masterOrderRepo: MasterOrderRepository, val tableRepo: TableRepository) {

  @Transactional
  fun checkin(tableId: Long): ResponseDto<String>{
    return try{
      val masterOrder = masterOrderRepo.createRecord(tableId, DateTimeHelper.getCurrentSQLDt())
          ?: throw RuntimeException("cannot create master order")
      tableRepo.assignMasterId(tableId, masterOrder.id)
      ResponseDto(true, "")
    } catch (e: Exception){ ResponseDto(false,"", e.message) }
  }

  @Transactional
  fun checkout(tableId: Long): ResponseDto<String>{
    return try {
      masterOrderRepo.checkout(tableId = tableId)
      tableRepo.assignMasterId(tableId, null)
      ResponseDto(true, "")
    } catch(e: Exception){
      ResponseDto(false, "")
    }
  }



}







