package ch.keepcalm.keepcalmfood.food

import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder

// Resource with self links
class FoodResource(
        var id: Int,
        var name: String,
        var synonyms: String? = null,
        var category: String? = null,
        var kcal: Int? = null,
        var fat: Double? = null,
        var protein: Double? = null
) : ResourceSupport() {

    constructor(f: Food) : this(
            f.id!!, f.name!!, f.synonyms, f.category, f.kcal, f.fat, f.protein
    )

    init {
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(FoodController::class.java).getFood(id)).withSelfRel())
    }
}