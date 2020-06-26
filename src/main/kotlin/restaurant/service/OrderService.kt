package restaurant.service

import org.springframework.stereotype.Service
import restaurant.domain.CreateOrderDTO
import restaurant.domain.ResponseDto
import restaurant.repository.OrderRepository

@Service
class OrderService(val orderRepo: OrderRepository) {

  fun createOrderInBatch(orderList: List<CreateOrderDTO>): ResponseDto<String> {
    orderList.map{ orderRepo.createOrder(it.orderQty, it.masterOrderId, it.foodId) }


  }

}


