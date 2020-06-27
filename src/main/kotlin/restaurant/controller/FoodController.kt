package restaurant.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import restaurant.domain.Category
import restaurant.domain.Foods
import restaurant.repository.FoodRepository

@RestController
@RequestMapping("/api/food")
class FoodController(val foodRepo: FoodRepository) {

  @GetMapping("/")
  fun findAll(): ResponseEntity<List<Foods>?>{
    val foods = foodRepo.findAll() ?: return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    return ResponseEntity.ok(foods)
  }

  @GetMapping("/category")
  fun findAllCategory(): ResponseEntity<List<Category>> {
    return try {
      ResponseEntity.ok(foodRepo.findDistinctCategory())
    } catch (e: Exception){
      ResponseEntity(listOf(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @PostMapping("")


}




