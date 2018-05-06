package ch.keepcalm.keepcalmfood.food

import com.fasterxml.jackson.annotation.JsonFilter

// Our domain object
// id,name,synonyms,category,kcal,fat,protein
@JsonFilter("foodFilter")
data class Food (

//        var id: Int,
//        var name: String,
//        var synonyms: String? = null,
//        var category: String? = null,
//        var kcal: Int? = null,
//        var fat: Double? = null,
//        var protein: Double? = null


        var id: Int? = null,
        var name: String? = null,
        var synonyms: String? = null,
        var category: String? = null,
        var kcal: Int? = null,
        var fat: Double? = null,
        var protein: Double? = null
)