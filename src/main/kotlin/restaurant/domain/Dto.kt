package restaurant.domain

data class ResponseDto<T> (
  var success: Boolean,
  var data: T,
  var reason: String? = null
)





