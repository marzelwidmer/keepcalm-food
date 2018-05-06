package ch.keepcalm.keepcalmfood.csvImporter

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class CsvFood {
    var id: String? = null
    var name: String? = null
    var name_scientific: String? = null
    var description: String? = null
    var food_group: String? = null
    var food_subgroup: String? = null
    var food_type: String? = null


    constructor() {}
    constructor(id: String?, name: String?, name_scientific: String?,
                description: String?, food_group: String?, food_subgroup: String?, food_type: String?) {
        this.id = id
        this.name = name
        this.name_scientific = name_scientific
        this.description = description
        this.food_group = food_group
        this.food_subgroup = food_subgroup
        this.food_type = food_type
    }

    override fun toString(): String {
        return "CsvFood [id=$id, name=$name]"
    }
}



fun loadCSVFoods(): MutableList<CsvFood>? {

    var fileReader: BufferedReader? = null
    var csvToBean: CsvToBean<CsvFood>?

    try {

        fileReader = BufferedReader(InputStreamReader(ClassPathResource("csv/foods.csv").inputStream, "UTF-8"))

        val mappingStrategy = ColumnPositionMappingStrategy<CsvFood>()
        mappingStrategy.setType(CsvFood::class.java)
        mappingStrategy.setColumnMapping("id", "name", "name_scientific", "description", "food_group", "food_subgroup", "food_type")

        csvToBean = CsvToBeanBuilder<CsvFood>(fileReader)
                .withMappingStrategy(mappingStrategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()

        val foods = csvToBean.parse()

        return foods

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
    return null
}
