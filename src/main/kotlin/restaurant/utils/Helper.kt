package restaurant.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeHelper {
  companion object {
    fun getCurrentSQLDt(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
  }
}

class SqlHelper {
  companion object {
    fun generateInString(ll: List<Long>): String = ll.joinToString(separator=",")
  }
}





