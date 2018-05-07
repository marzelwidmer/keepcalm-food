package ch.keepcalm.keepcalmfood

import ch.keepcalm.keepcalmfood.food.FoodRepository
import ch.keepcalm.keepcalmfood.food.FoodService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@SpringBootApplication
class KeepcalmFoodApplication(val foodService: FoodService, val foodRepository: FoodRepository) {

    @Bean
    fun init() = CommandLineRunner {
        val count = foodRepository.count()
        println("found $count records in database")
    }
}

fun main(args: Array<String>) {
    runApplication<KeepcalmFoodApplication>(*args)
}

@Component
@Profile("dev")
class DevDatabaseLoader(val foodService: FoodService, val foodRepository: FoodRepository) {

    init {
        println("load database....")
        foodService.loadDatabase()
        val count = foodRepository.count()
        println("... found $count records in database after db load")
    }

}


