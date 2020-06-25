package restaurant.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import restaurant.config.GeneralConfig
import restaurant.domain.Foods
import restaurant.domain.ResponseDto
import restaurant.repository.FoodRepository
import java.lang.RuntimeException

data class FoodList (val id: Long, val qty: Int)

@Service
class FoodService(val foodRepo: FoodRepository) {

  fun getAllByPage(page: Int): Page<Foods> = foodRepo.findAll(PageRequest.of(page, GeneralConfig["pageSize"] as Int))

  fun updateFood(food: Foods) = foodRepo.save(food)

  fun consumeFoodList(foodList: List<FoodList>): ResponseDto<List<Long>> {
    val res = foodList.map{consumeFood(it.id, it.qty)}
    val failedFoodId = res.filter { !it.success }.map { it.data }

    if(failedFoodId.isEmpty()) return ResponseDto(true, listOf())
    return ResponseDto(false, failedFoodId, "failed")

  }

  fun consumeFood(foodId: Long, qty: Int): ResponseDto<Long> {
    try {
      val food = foodRepo.findById(foodId)
      if(food.isEmpty) return ResponseDto(false, foodId, "Food does not exist")
      if(food.get().quantity < qty) return ResponseDto(false, foodId, "Food does not have enough quantity")
      foodRepo.reduceQty(qty, foodId)

      return ResponseDto(true, -1)

    } catch (e: Exception){
      return ResponseDto(false, foodId, e.message)
    }
  }




}

