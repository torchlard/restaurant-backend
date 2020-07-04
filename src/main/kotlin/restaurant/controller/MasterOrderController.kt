package restaurant.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import restaurant.domain.*
import restaurant.repository.MasterOrderRepository
import restaurant.service.MasterOrderService
import java.lang.Exception


@RestController
@RequestMapping("/api/masterorder")
class MasterOrderController (private val masterOrderService: MasterOrderService, val repo: MasterOrderRepository){

  @PostMapping("/checkin")
  fun checkin(@RequestBody dto: CheckinDTO): HttpStatus {
    return try {
      if(masterOrderService.checkin(dto.tableId).success) HttpStatus.OK
      else HttpStatus.BAD_REQUEST
    }
    catch(e: IllegalArgumentException){ HttpStatus.BAD_REQUEST }
    catch(e: Exception){
      e.printStackTrace()
      HttpStatus.INTERNAL_SERVER_ERROR
    }
  }

  @PostMapping("/checkout")
  fun checkout(@RequestBody dto: CheckinDTO): HttpStatus {
    return if(masterOrderService.checkout(dto.tableId).success) HttpStatus.OK
      else HttpStatus.BAD_REQUEST
  }



}










