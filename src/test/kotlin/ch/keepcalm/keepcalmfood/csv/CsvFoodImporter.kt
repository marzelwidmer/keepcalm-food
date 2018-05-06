package ch.keepcalm.keepcalmfood.csv

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


//
//class Food {
//    var id: String? = null
//    var name: String? = null
//    var name_scientific: String? = null
//    var description: String? = null
//    var food_group: String? = null
//    var food_subgroup: String? = null
//    var food_type: String? = null
//
//
//    constructor() {}
//    constructor(id: String?, name: String?, name_scientific: String?,
//                description: String?, food_group: String?, food_subgroup: String?, food_type: String?) {
//        this.id = id
//        this.name = name
//        this.name_scientific = name_scientific
//        this.description = description
//        this.food_group = food_group
//        this.food_subgroup = food_subgroup
//        this.food_type = food_type
//    }
//
//    override fun toString(): String {
//        return "Food [id=$id, name=$name]"
//    }
//}

//http://javasampleapproach.com/kotlin/kotlin-how-to-read-write-csv-file-with-opencsv-example

fun main(args: Array<String>?) {

    var fileReader: BufferedReader? = null
    var csvToBean: CsvToBean<Food>?

    try {
        fileReader = BufferedReader(FileReader("src/main/resources/csv/SwissFoodCompData-v5.3.csv"))

        val mappingStrategy = ColumnPositionMappingStrategy<Food>()
        mappingStrategy.setType(Food::class.java)


        mappingStrategy.setColumnMapping("id", "name", "synonyms", "category", "kcal", "fat", "protein")


        csvToBean = CsvToBeanBuilder<Food>(fileReader)
                .withMappingStrategy(mappingStrategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()

        val foods = csvToBean.parse()
        for (food in foods) {
            println(food)
        }
    } catch (e: Exception) {
        println("Reading CSV Error!")
        e.printStackTrace()
    } finally {
        try {
            fileReader!!.close()
        } catch (e: IOException) {
            println("Closing fileReader/csvParser Error!")
            e.printStackTrace()
        }
    }
}


data class Food (

    var id: Int? = null,
    var name: String? = null,
    var synonyms: String? = null,
    var category: String? = null,
    var kcal: Int? = null,
    var fat: Double? = null,
    var protein: Double? = null
)


