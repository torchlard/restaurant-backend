package restaurant.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restaurant.domain.ArriveOrderDTO
import restaurant.domain.OrderDTO
import restaurant.domain.OrderListDTO
import restaurant.repository.OrderRepository
import restaurant.service.OrderService

@RestController
@RequestMapping("/api/order")
class OrderController(private val orderRepo: OrderRepository, private val orderService: OrderService) {

  //  client create orders for a table
  @PostMapping("/orders")
  fun batchCreateOrders(@RequestBody dto: OrderListDTO<OrderDTO>): ResponseEntity<List<Long>> {
    val res = orderService.createOrderInBatch(dto.tableId, dto.ll)
    if(res.success) return ResponseEntity.ok(res.data)
    return ResponseEntity(res.data, HttpStatus.BAD_REQUEST)
  }

  //  client delete orders from a table
  @DeleteMapping("/orders")
  fun deleteOrders(@RequestBody dto: OrderListDTO<Long>): HttpStatus {
    if(orderService.deleteOrderInBatch(dto.tableId, dto.ll).success) return HttpStatus.OK
    else return HttpStatus.BAD_REQUEST
  }

  //  client change ordered quantity in batch
  @PutMapping("/orderqty")
  fun updateOrders(@RequestBody dto: OrderListDTO<OrderDTO>): ResponseEntity<List<Long>> {
    val res = orderService.updateOrderInBatch(dto.tableId, dto.ll)
    if(res.success) return ResponseEntity.ok(res.data)
    return ResponseEntity(res.data, HttpStatus.BAD_REQUEST)
  }

  //  food arrived to client
  @PutMapping("/arriveqty")
  fun updateArriveQty(@RequestBody dto: OrderListDTO<ArriveOrderDTO>): ResponseEntity<List<Long>> {
    val res = orderService.updateArriveQtyInBatch(dto.tableId, dto.ll)
    if(res.success) return ResponseEntity.ok(res.data)
    return ResponseEntity(res.data, HttpStatus.BAD_REQUEST)
  }


}