package restaurant

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import restaurant.repository.*
import restaurant.service.DataInit

@SpringBootApplication
class RestaurantApplication(val foodRepo: FoodRepository, val masterOrderRepo: MasterOrderRepository,
						val orderRepo: OrderRepository, val staffRepo: StaffRepository, val tableRepo: TableRepository): CommandLineRunner {
	override fun run(vararg args: String?) {

		DataInit.initTable(foodRepo, masterOrderRepo, orderRepo, staffRepo, tableRepo)

	}

}

fun main(args: Array<String>) {
	runApplication<RestaurantApplication>(*args)
}

