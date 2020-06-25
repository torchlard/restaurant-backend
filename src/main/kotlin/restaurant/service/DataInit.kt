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

    val tables = listOf(
      Tables(1,"T1",10,false),
      Tables(2,"T2",8,false),
      Tables(3,"T3",7,false),
      Tables(4,"T4",4,true),
      Tables(5,"T5",4,true),
      Tables(6,"T6",4,true),
      Tables(7,"T7",4,true),
      Tables(8,"T8",6,true),
      Tables(9,"T9",5,true)
    )

    val masterOrders = listOf(
      MasterOrders(id=1,checkinDt = LocalDateTime.of(2020,1,1,10,0,0), checkoutDt=LocalDateTime.of(2020,1,1,12,0,0),moneyReturn = 15f,payment =  30f,price =  15f,staff = staffs[0], table = tables[0], status = Status.Completed),
      MasterOrders(id=2,staff=staffs[1],table=tables[1]),
      MasterOrders(id=3,staff=staffs[2],table=tables[2])
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
      masterOrderRepo.deleteAllInBatch()
      staffRepo.deleteAllInBatch()
      foodRepo.deleteAllInBatch()
      tableRepo.deleteAllInBatch()

      tableRepo.saveAll(tables)
      tableRepo.flush()
      foodRepo.saveAll(foods)
      foodRepo.flush()
      staffRepo.saveAll(staffs)
      staffRepo.flush()
      val staffAll = staffRepo.findAll()
      val tableAll = tableRepo.findAll()
      val foodAll = foodRepo.findAll()
      val masterOrders = listOf(
        MasterOrders(id=1, checkinDt = LocalDateTime.of(2020,1,1,10,0,0), checkoutDt=LocalDateTime.of(2020,1,1,12,0,0),
          moneyReturn = 15f,payment =  30f,price =  15f,staff = staffAll.random(), table = tableAll.random(), status = Status.Completed)) +
          (1..2).map{ MasterOrders(id=1, staff=staffAll.random(),table=tableAll.random()) }
      masterOrderRepo.saveAll(masterOrders)
      masterOrderRepo.flush()
      val masterOrdersAll = masterOrderRepo.findAll()
      val orders = listOf(
        Orders(1,1,1, masterOrdersAll.random(), foodAll.random()),
        Orders(2,3,2, masterOrdersAll.random(), foodAll.random()),
        Orders(3,2,1, masterOrdersAll.random(), foodAll.random()),
        Orders(4,3,3, masterOrdersAll.random(), foodAll.random())
      )
      orderRepo.saveAll(orders)

    }

  }

}















