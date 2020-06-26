package restaurant.controller

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restaurant.domain.CheckinDTO
import restaurant.domain.CreateOrderDTO
import restaurant.domain.CreateOrderListDTO
import restaurant.repository.OrderRepository
import restaurant.service.FoodService
import restaurant.service.MasterOrderService
import restaurant.service.OrderService
import java.lang.Exception


@RestController
@RequestMapping("/api/masterorder")
class MasterOrderController (private val masterOrderService: MasterOrderService){

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


@RestController
@RequestMapping("/api/order")
class OrderController(private val orderRepo: OrderRepository, private val orderService: OrderService) {

  @PostMapping("/order")
  fun createOrder(@RequestBody dto: CreateOrderDTO): HttpStatus {
    try {
      orderRepo.createOrder(dto.orderQty, dto.masterOrderId, dto.foodId)
      return HttpStatus.OK
    } catch (e: Exception){ return HttpStatus.INTERNAL_SERVER_ERROR }
  }

  @PostMapping("/orders")
  fun batchCreateOrders(@RequestBody dto: CreateOrderListDTO): HttpStatus {
    orderService.createOrderInBatch(dto.ll)
  }



}







