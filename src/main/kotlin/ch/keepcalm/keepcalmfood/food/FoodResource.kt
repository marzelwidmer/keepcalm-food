package ch.keepcalm.keepcalmfood.food

import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder

// Resource with self links
class FoodResource(val id: Int, val name: String, val name_scientific: String, val description: String, val food_group: String, val food_subgroup: String) : ResourceSupport() {

    constructor(f: Food) : this(f.id, f.name, f.name_scientific, f.description, f.food_group, f.food_subgroup)

    init {
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(FoodController::class.java).getFood(id)).withSelfRel())
    }
}