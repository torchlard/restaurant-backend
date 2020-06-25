package restaurant.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GeneratedValue

enum class Category(val cat: String) {
  Burger("burger"), Pizza("pizza"), Sides("sides"),
  Chicken("chicken"), Drinks("drinks"), Desserts("desserts")
}

enum class Status(val status: String) {
  Completed("completed"), Serving("serving")
}

enum class Gender(val gender: String) { M("M"),F("F") }
enum class Position(val pos: String) {
  Admin("admin"), Worker("worker")
}

@Entity
data class Foods (
  @Id @GeneratedValue var id: Long,
  var name: String,
  var price: Float,
  var quantity: Int,
  @Enumerated(EnumType.STRING)
  var category: Category
)

@Entity
data class MasterOrders (
  @Id @GeneratedValue var id: Long,
  var checkinDt: LocalDateTime = LocalDateTime.now(),
  var checkoutDt: LocalDateTime? = null,
  var moneyReturn: Float? = null,
  var payment: Float? = null,
  var price: Float? = null,
  @ManyToOne var staff: Staffs,
  @ManyToOne var table: Tables,
  @Enumerated(EnumType.STRING)
  var status: Status = Status.Serving
)

@Entity
data class Orders (
  @Id @GeneratedValue var id: Long,
  var orderedQty: Int,
  var arrivedQty: Int,
  @ManyToOne var masterOrder: MasterOrders,
  @ManyToOne var food: Foods
)

@Entity
data class Staffs (
  @Id @GeneratedValue var id: Long,
  var age: Int,
  var contactNum: String,
  var firstName: String,
  var lastName: String,
  @Enumerated(EnumType.STRING)
  var gender: Gender,
  @Enumerated(EnumType.STRING)
  var position: Position,
  var username: String? = null,
  var password: String?= null
)

@Entity
data class Tables (
  @Id @GeneratedValue var id: Long,
  var tableNo: String,
  var numOfSeat: Int,
  var available: Boolean
)














