package restaurant.service

import restaurant.domain.*
import restaurant.repository.*
import java.time.LocalDateTime

class DataInit {

  companion object {
    val foods = listOf(
      Foods(1, "Angus Burger",30f,600,Category.Burger),
      Foods(2, "Chicken Muffin", 20f, 600, Category.Burger),
      Foods(3, "Grill Chicken Burger",24f, 500, Category.Burger),
      Foods(4, "Double Cheese & Beef Burger", 22f, 600, Category.Burger),
      Foods(5, "Hawaiian Pizza", 90f, 300, Category.Pizza),
      Foods(6, "Margherita Pizza", 90f, 300, Category.Pizza),
      Foods(7, "Pepperoni Passion Pizza", 100f, 300, Category.Pizza),
      Foods(8, "Regular Fries", 10f, 999, Category.Sides),
      Foods(9, "Large Fries", 10f, 999, Category.Sides),
      Foods(10, "Garlic Bread", 10f, 999, Category.Sides),
      Foods(11, "Potato Wedge", 15f, 999, Category.Sides),
      Foods(12, "Grill Chicken Wings", 30f, 1000, Category.Chicken),
      Foods(13, "Fried Chicken Wings", 30f, 999, Category.Chicken),
      Foods(14, "BBQ Chicken Wings", 40f, 999, Category.Chicken),
      Foods(15, "Fanta Orange Soda", 10f, 500, Category.Drinks),
      Foods(16, "Lemon Tea", 10f, 500, Category.Drinks),
      Foods(17, "Orange Juice", 12f, 500, Category.Drinks),
      Foods(18, "Original Cheesecake", 18f, 200, Category.Desserts),
      Foods(19, "Chocolate Cheesecake", 18f, 200, Category.Desserts),
      Foods(20, "Strawberry Cheesecake", 18f, 200, Category.Desserts)
    )
    
    val staffs = listOf(
      Staffs(1,23,"97567830","John","Clay",Gender.M,Position.Admin,"john","1234"),
      Staffs(2,25,"97564260","Mary","Sun",Gender.F,Position.Worker,"mary","2345"),
      Staffs(3,29,"47567830","May","Chan",Gender.F,Position.Worker,"may","5678")
    )


    val masterOrders = listOf(
        MasterOrders(1,LocalDateTime.of(2020,1,1,10,0,0), LocalDateTime.of(2020,1,1,12,0,0), 15f, 30f, 15f, staffs[0], Status.Completed),
        MasterOrders(id=2,staff=staffs[1]),
        MasterOrders(id=3,staff=staffs[2])
    )

    val tables = listOf(
      Tables(1,"T1",10),
      Tables(2,"T2",8, masterOrders[1]),
      Tables(3,"T3",7, masterOrders[2]),
      Tables(4,"T4",4),
      Tables(5,"T5",4),
      Tables(6,"T6",4),
      Tables(7,"T7",4),
      Tables(8,"T8",6),
      Tables(9,"T9",5)
    )

    val orders = listOf(
      Orders(1,1,1, masterOrders[0],foods[16]),
      Orders(2,3,2, masterOrders[1],foods[1]),
      Orders(3,2,1, masterOrders[1],foods[5]),
      Orders(4,3,3, masterOrders[0],foods[10])
    )


    fun initTable(foodRepo: FoodRepository, masterOrderRepo: MasterOrderRepository, 
                  orderRepo: OrderRepository,staffRepo: StaffRepository, tableRepo: TableRepository){
      orderRepo.deleteAllInBatch()
      tableRepo.deleteAllInBatch()
      masterOrderRepo.deleteAllInBatch()
      foodRepo.deleteAllInBatch()
      staffRepo.deleteAllInBatch()

      orderRepo.resetAutoIncrement()
      masterOrderRepo.resetAutoIncrement()
      staffRepo.resetAutoIncrement()
      foodRepo.resetAutoIncrement()
      tableRepo.resetAutoIncrement()

      staffRepo.saveAll(staffs)
      foodRepo.saveAll(foods)
      masterOrderRepo.saveAll(masterOrders)
      tableRepo.saveAll(tables)
      orderRepo.saveAll(orders)
    }

  }

}















