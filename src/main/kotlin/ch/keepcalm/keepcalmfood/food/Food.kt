package ch.keepcalm.keepcalmfood.food

import com.fasterxml.jackson.annotation.JsonFilter

// Our domain object
@JsonFilter("foodFilter")
data class Food(
        val id: Int,
        val name: String,
        val name_scientific: String,
        val description: String,
        val food_group: String,
        val food_subgroup: String
)