package restaurant.controller

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restaurant.service.FoodService
import restaurant.service.MasterOrderService
import java.lang.Exception

data class CheckinDTO @JsonCreator constructor(val tableNo: Long)

@RestController
@RequestMapping("/api/masterorder")
class MasterOrderController (
  private val masterOrderService: MasterOrderService
){

  @PostMapping("/checkin")
  fun checkin(@RequestBody dto: CheckinDTO): HttpStatus {
    try {
      return if(masterOrderService.checkin(dto.tableNo).success) HttpStatus.OK else HttpStatus.BAD_REQUEST
    }
    catch(e: IllegalArgumentException){ return HttpStatus.BAD_REQUEST }
    catch (e: Exception){ return HttpStatus.INTERNAL_SERVER_ERROR }
  }

  @PostMapping("/checkout")


}










