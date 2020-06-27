package restaurant.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restaurant.domain.Category
import restaurant.domain.FoodDTO
import restaurant.domain.Foods
import restaurant.repository.FoodRepository
import restaurant.service.FoodService

@RestController
@RequestMapping("/api/food")
class FoodController(val foodRepo: FoodRepository, val foodService: FoodService) {

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
  fun createFood(@RequestBody dto: FoodDTO): HttpStatus {
    return try {
      foodRepo.save(Foods(null, dto.name, dto.price, dto.quantity, dto.category, false))
      HttpStatus.OK
    } catch(e: Exception){
      HttpStatus.INTERNAL_SERVER_ERROR
    }
  }

  @DeleteMapping("/{foodId}")
  fun deleteFood(@PathVariable foodId: Long) =
    try { foodRepo.deleteFood(foodId); HttpStatus.OK }
    catch(e : Exception) { HttpStatus.INTERNAL_SERVER_ERROR }




}




