package restaurant.service

import org.springframework.stereotype.Service
import restaurant.domain.ArriveOrderDTO
import restaurant.domain.OrderDTO
import restaurant.domain.ResponseDto
import restaurant.repository.OrderRepository
import restaurant.repository.TableRepository
import restaurant.utils.SqlHelper
import java.lang.RuntimeException
import java.sql.SQLException

@Service
class OrderService(val orderRepo: OrderRepository, val tableRepo: TableRepository) {

  fun createOrderInBatch(tableId: Long, orderList: List<OrderDTO>): ResponseDto<List<Long>> {
    return try {
      val masterOrderId = tableRepo.findMasterOrderByTableNo(tableId)
          ?: throw RuntimeException("no master order found")
      val foodList: List<Long> = orderList.map {
        try { orderRepo.createOrder(it.orderQty, masterOrderId, it.foodId); -1
        } catch(e: Exception){ println("error: "+e.message); it.foodId }
      }.filter { it != -1L }

      ResponseDto(foodList.isEmpty(), foodList)
    } catch(e: Exception){
      ResponseDto(false, listOf())
    }
  }

  fun deleteOrderInBatch(tableId: Long, foodIds: List<Long>): ResponseDto<String> {
    return try {
      val masterOrderId = tableRepo.findMasterOrderByTableNo(tableId)
          ?: throw RuntimeException("no master order found")
      orderRepo.deleteOrdersInBatch(masterOrderId, foodIds.joinToString(","))
      ResponseDto(true, "")
    } catch (e: Exception){
      ResponseDto(false, "")
    }
  }

  fun updateOrderInBatch(tableId: Long, ll: List<OrderDTO>): ResponseDto<List<Long>> {
    return try {
      val masterOrderId = tableRepo.findMasterOrderByTableNo(tableId)
          ?: throw RuntimeException("no master order found")
      val failedId = mutableListOf<Long>()
      ll.forEach {
        val arriveQty = orderRepo.findByNo(masterOrderId, it.foodId)
        if(arriveQty != null &&  it.orderQty >= arriveQty ){
          try {
            orderRepo.updateOrderQuantity(it.orderQty, it.foodId, masterOrderId)
          } catch (ex: SQLException){
            failedId.add(it.foodId)
          }
        } else {
          failedId.add(it.foodId)
        }
      }
      ResponseDto(true, failedId)
    } catch(e: Exception) {
      ResponseDto(false, listOf())
    }
  }

  fun updateArriveQtyInBatch(tableId: Long, ll: List<ArriveOrderDTO>): ResponseDto<List<Long>> {
    return try {
      val masterOrderId = tableRepo.findMasterOrderByTableNo(tableId)
          ?: throw RuntimeException("no master order found")
      val failedId = mutableListOf<Long>()
      ll.forEach {
        try {
          if(it.arriveQty < 0) throw RuntimeException("Arrive Quantity must be greater than 0")
          orderRepo.updateArriveQuantity(it.arriveQty, it.foodId, masterOrderId)
        } catch (ex: Exception){
          failedId.add(it.foodId)
        }
      }
      ResponseDto(true, failedId)
    } catch(e: Exception) {
      ResponseDto(false, listOf())
    }
  }




}


