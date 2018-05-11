package ch.keepcalm.keepcalmfood.food

import ch.keepcalm.keepcalmfood.HalResource
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


// Complete rest controller with list of foods and detail urls
@RestController
class FoodController(val foodService: FoodService) {


    @GetMapping(value = ["/foods"])
    fun getFoods(): ResponseEntity<HalResource> {

        val foodLinkResources = foodService.findAllFoods()!!.map { food: Food ->
            FoodLinkResource(food)
        }
        val foodResource = HalResource()
        foodResource.embedResource("foods", foodLinkResources)
        foodResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn<FoodController>(FoodController::class.java).getFoods()).withSelfRel())

        return ok(foodResource)
    }

    @GetMapping(value = ["/foods/{id}"])
    fun getFood(@PathVariable id: String): ResponseEntity<FoodResource> {
        val food = foodService.findOne(id)
         return ResponseEntity.ok(FoodResource(food))
    }



    //http://localhost:8080/foods/634?fields=name
    @GetMapping(value = ["/foods/{id}"], params = ["fields"])
    fun getFoodsWithSomeFields(@PathVariable id: String, @RequestParam fields: Array<String>): MappingJacksonValue {
        val food = foodService.findOne(id)

        val wrapper = MappingJacksonValue(food)
        val filterProvider = SimpleFilterProvider().addFilter("foodFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(*fields))
        wrapper.filters = filterProvider
        return wrapper
    }
}



