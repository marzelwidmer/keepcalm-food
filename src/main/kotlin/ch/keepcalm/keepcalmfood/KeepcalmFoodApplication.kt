package ch.keepcalm.keepcalmfood

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeepcalmFoodApplication{

//    @Bean
//    fun init() = CommandLineRunner {
//       print("command runner....")
//    }

}

fun main(args: Array<String>) {
    runApplication<KeepcalmFoodApplication>(*args)
}
