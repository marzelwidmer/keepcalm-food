package ch.keepcalm.keepcalmfood

import ch.keepcalm.keepcalmfood.food.FoodRepository
import ch.keepcalm.keepcalmfood.food.FoodService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class KeepcalmFoodApplication(val foodService: FoodService, val foodRepository: FoodRepository) {

    @Bean
    fun init() = CommandLineRunner {
       print("command runner....")
        val count = foodRepository.count()
        foodService.loadDatabase()
        println("found $count records in database after db load")
    }

}

fun main(args: Array<String>) {
    runApplication<KeepcalmFoodApplication>(*args)
}

