package ch.keepcalm.keepcalmfood

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import java.util.*






@SpringBootApplication
class KeepcalmFoodApplication

fun main(args: Array<String>) {
    runApplication<KeepcalmFoodApplication>(*args)
}

@Configuration
internal class WebMvcConfiguration : WebMvcConfigurationSupport() {
    companion object {
        fun extendMessageConverters(converters: List<HttpMessageConverter<*>>?) {
            for (converter in converters!!) {
                if (converter is MappingJackson2HttpMessageConverter) {
                    val mapper = converter.objectMapper
                    mapper.setFilterProvider(SimpleFilterProvider().addFilter("foodFilter", SimpleBeanPropertyFilter.serializeAll()))
                }
            }
        }
    }
}

// Our domain object
@JsonFilter("foodFilter")
data class Food(
        val id: Int,
        val name: String,
        val name_scientific: String,
        val description: String,
        val food_group: String,
        val food_subgroup: String,
        val food_type: String
)


// Resource with self links
class FoodResource(val id: Int, val name: String, val name_scientific: String, val description: String, val food_group: String, val food_subgroup: String, val food_type: String) : ResourceSupport() {
    constructor(f: Food) : this(f.id, f.name, f.name_scientific, f.description, f.food_group, f.food_subgroup, f.food_type)

    init {
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(FoodController::class.java).getFood(id)).withSelfRel())
    }
}

// Resource with link to available foods
class FoodLinkResource(val id: Int, val name: String) : ResourceSupport() {
    constructor(s: Food) : this(s.id, s.name)

    init {
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(FoodController::class.java).getFood(id)).withRel("food"))
    }
}

// Complete rest controller with list of foods and detail urls
@RestController
class FoodController {

    companion object {
        val foods = listOf(
                Food(id = 1, name = "Angelica", name_scientific = "Angelica keiskei", description = "Angelica is a genus of about 60 species of tall biennial and perennial herbs in the family Apiaceae, native to temperate and subarctic regions of the Northern Hemisphere, reaching as far north as Iceland and Lapland. They grow to 1‰ÛÒ3 m tall, with large bipinnate leaves and large compound umbels of white or greenish-white flowers. Some species can be found in purple moor and rush pastures.",
                        food_group = "Herbs and Spices", food_subgroup = "Herbs", food_type = "Type 1"),
                Food(id = 634, name = "Yogurt", name_scientific = "", description = "\"Yogurt, yoghurt, or yoghourt is a fermented milk product produced by bacterial fermentation of milk. The bacteria used to make yogurt are known as \"\"yogurt cultures\"\". Fermentation of lactose by these bacteria produces lactic acid, which acts on milk protein to give yogurt its texture and its characteristic tang.\n" +
                        "Worldwide, cow's milk, the protein of which is mainly casein, is most commonly used to make yogurt. Milk from water buffalo, goats, ewes, mares, camels, and yaks however, is also used to produce yogurt in various parts of the world.\"",
                        food_group = "Milk and milk products", food_subgroup = "Fermented milk products", food_type = "Type 2")
        )
    }

    @GetMapping(value = ["/foods"])
    fun getFoods(): ResponseEntity<HalResource> {
        val foodLinkResources = foods.map { food: Food ->
            FoodLinkResource(food)
        }

        val foodResource = HalResource()
        foodResource.embedResource("foods", foodLinkResources)
        foodResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn<FoodController>(FoodController::class.java).getFoods()).withSelfRel())

        return ResponseEntity.ok(foodResource)
    }

    @GetMapping(value = ["/foods/{id}"])
    fun getFood(@PathVariable id: Int): ResponseEntity<FoodResource> {
        val food = foods.find { f -> f.id.equals(id) }
        return if (food == null)
            ResponseEntity<FoodResource>(null, HttpStatus.NOT_FOUND)
        else
            ResponseEntity.ok(FoodResource(food))
    }



    //http://localhost:8080/foods/634?fields=name
    @GetMapping(value = ["/foods/{id}"], params  = ["fields"] )
    fun getFoodsWithSomeFields(@PathVariable id: Int, @RequestParam fields: Array<String>): MappingJacksonValue {
        val food = foods.find { f -> f.id.equals(id) }

        val wrapper = MappingJacksonValue(food!!)
        val filterProvider = SimpleFilterProvider().addFilter("foodFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(*fields))
        wrapper.filters = filterProvider
        return wrapper
    }
}


class HalResource : ResourceSupport() {

    private val embedded = HashMap<String, Any>()

    val embeddedResources: Map<String, Any>
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @JsonProperty("_embedded")
        get() = embedded

    fun embedResource(relationship: String, resource: ResourceSupport) {
        embedded[relationship] = resource

    }

    fun embedResource(relationship: String, resourceList: List<*>) {
        embedded[relationship] = resourceList
    }
}
