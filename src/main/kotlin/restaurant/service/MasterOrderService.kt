package restaurant.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import restaurant.domain.MasterOrders
import restaurant.domain.ResponseDto
import restaurant.domain.Status
import restaurant.repository.MasterOrderRepository
import restaurant.repository.StaffRepository
import restaurant.repository.TableRepository
import restaurant.utils.DateTimeHelper
import java.lang.Exception
import java.lang.RuntimeException
import java.time.LocalDateTime

@Service
class MasterOrderService(val staffRepo: StaffRepository,
    val masterOrderRepo: MasterOrderRepository, val tableRepo: TableRepository) {

  @Transactional(propagation = Propagation.SUPPORTS)
  fun checkin(tableId: Long): ResponseDto<String>{
    return try{
      val mo = MasterOrders(checkinDt = LocalDateTime.now(), status = Status.Serving)
      val inserted = masterOrderRepo.save(mo)
      tableRepo.assignMasterId(tableId, inserted.id)
      ResponseDto(true, "")
    } catch (e: Exception){
      e.printStackTrace()
      ResponseDto(false,"", e.message)
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  fun checkout(tableId: Long): ResponseDto<String>{
    return try {
      masterOrderRepo.checkout(tableId = tableId)
      tableRepo.assignMasterId(tableId, null)
      ResponseDto(true, "")
    } catch(e: Exception){
      e.printStackTrace()
      ResponseDto(false, "")
    }
  }

}







