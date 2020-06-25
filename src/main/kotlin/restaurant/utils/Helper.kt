package restaurant.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeHelper {
  companion object {
    fun getCurrentSQLDt(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss"))
  }
}






