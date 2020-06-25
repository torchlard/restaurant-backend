package restaurant.service

import org.springframework.stereotype.Service
import restaurant.domain.MasterOrders
import restaurant.domain.ResponseDto
import restaurant.repository.MasterOrderRepository
import restaurant.utils.DateTimeHelper
import java.lang.Exception
import java.time.LocalDateTime

@Service
class MasterOrderService(val masterOrderRepo: MasterOrderRepository) {

  fun checkin(tableId: Long): ResponseDto<String>{
    try{
      masterOrderRepo.checkin(tableId, DateTimeHelper.getCurrentSQLDt())
      return ResponseDto(true, "")
    } catch (e: Exception){ return ResponseDto(false,"", e.message) }
  }

  fun checkout(id: Long) = masterOrderRepo.checkout(LocalDateTime.now(), id)



}







