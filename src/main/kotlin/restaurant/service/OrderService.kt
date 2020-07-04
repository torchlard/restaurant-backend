package restaurant.service

import org.springframework.stereotype.Service
import restaurant.domain.ArriveOrderDTO
import restaurant.domain.OrderDTO
import restaurant.domain.ResponseDto
import restaurant.repository.FoodRepository
import restaurant.repository.OrderRepository
import restaurant.repository.TableRepository
import java.lang.RuntimeException
import java.sql.SQLException

@Service
class OrderService(val foodRepo: FoodRepository, val orderRepo: OrderRepository, val tableRepo: TableRepository) {

//  client create order
  fun createOrderInBatch(tableId: Long, orderList: List<OrderDTO>): ResponseDto<List<Long>> {
    return try {
      val masterOrderId = tableRepo.findMasterOrderByTableNo(tableId)
          ?: throw RuntimeException("no master order found")
//      verify food enough
      val qtyIds = foodRepo.selectQtyInBatch(orderList.map { it.foodId }.joinToString(","))

      val failedfoodList = mutableListOf<Long>()
      orderList.forEach { orderObj ->
        val ll = qtyIds.filter { it.foodId == orderObj.foodId }
//        food not exist OR not enough food to order
        if(!ll.isEmpty() && ll[0].qty > orderObj.orderQty){
          try {
            orderRepo.createOrder(orderObj.orderQty, masterOrderId, orderObj.foodId)
          } catch(e: Exception){
            println("error: "+e.message)
            failedfoodList.add(orderObj.foodId)
          }
        }
      }

      ResponseDto(failedfoodList.isEmpty(), failedfoodList)
    } catch(e: Exception){
      e.printStackTrace()
      ResponseDto(false, listOf())
    }
  }

  fun deleteOrderInBatch(tableId: Long, foodIds: List<Long>): ResponseDto<String> {
    return try {
      val masterOrderId = tableRepo.findMasterOrderByTableNo(tableId) ?: throw RuntimeException("no master order found")
      foodIds.forEach {
        val order = orderRepo.selectOrder(masterOrderId, it)
        if(order.arriveQty == 0){
          orderRepo.deleteOrder(masterOrderId, it)
          if(order.orderQty > 0) foodRepo.changeQty(order.orderQty, it)
        }
      }
      ResponseDto(true, "")
    } catch (e: Exception){
      e.printStackTrace()
      ResponseDto(false, "")
    }
  }

//  client update order
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
      e.printStackTrace()
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
      e.printStackTrace()
      ResponseDto(false, listOf())
    }
  }




}


