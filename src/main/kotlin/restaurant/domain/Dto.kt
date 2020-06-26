package restaurant.domain

import com.fasterxml.jackson.annotation.JsonCreator

data class ResponseDto<T> (
  var success: Boolean,
  var data: T,
  var reason: String? = null
)

data class CheckinDTO @JsonCreator constructor(val tableNo: Long)
data class CreateOrderDTO @JsonCreator constructor(val orderQty: Int, val masterOrderId: Long, val foodId: Long)
data class QtyDTO @JsonCreator constructor(val qty: Int, val id: Long)

data class CreateOrderListDTO @JsonCreator constructor(val ll: List<CreateOrderDTO>)


