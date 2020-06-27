package restaurant.domain

import com.fasterxml.jackson.annotation.JsonCreator

data class ResponseDto<T> (
  var success: Boolean,
  var data: T,
  var reason: String? = null
)

data class CheckinDTO @JsonCreator constructor(val tableId: Long)
//data class CreateOrderDTO @JsonCreator constructor(val orderQty: Int, val tableNo: String, val foodId: Long)
//data class QtyDTO @JsonCreator constructor(val qty: Int, val id: Long)

data class OrderDTO @JsonCreator constructor(val orderQty: Int, val foodId: Long)
data class ArriveOrderDTO @JsonCreator constructor(val arriveQty: Int, val foodId: Long)
data class OrderListDTO<T> @JsonCreator constructor(val tableId: Long, val ll: List<T>)
//data class DeleteOrderListDTO @JsonCreator constructor(val tableNo: String, val foodList: List<Long>)

data class FoodDTO @JsonCreator constructor(val name: String, val price: Float, val quantity: Int = 0, val category: Category)



