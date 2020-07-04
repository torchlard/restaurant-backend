package restaurant.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import restaurant.config.GeneralConfig
import restaurant.domain.Foods
import restaurant.domain.ResponseDto
import restaurant.repository.FoodRepository

data class FoodList (val id: Long, val qty: Int)

@Service
class FoodService(val foodRepo: FoodRepository) {

  fun getAllByPage(page: Int): Page<Foods> = foodRepo.findAll(PageRequest.of(page, GeneralConfig["pageSize"] as Int))

  fun updateFood(food: Foods) = foodRepo.save(food)



}

