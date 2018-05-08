package ch.keepcalm.keepcalmfood

import ch.keepcalm.keepcalmfood.food.FoodRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class KeepcalmFoodApplication(val foodRepository: FoodRepository) {

    @Bean
    fun init() = CommandLineRunner {
        val count = foodRepository.count()
        println("found $count records in database")
    }
}

fun main(args: Array<String>) {
    runApplication<KeepcalmFoodApplication>(*args)
}

