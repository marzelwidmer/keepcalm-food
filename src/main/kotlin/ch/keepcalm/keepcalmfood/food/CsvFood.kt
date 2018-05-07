package ch.keepcalm.keepcalmfood.food

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


fun cvsToFoodList(): MutableList<Food>? {

    var fileReader: BufferedReader? = null
    var csvToBean: CsvToBean<Food>?

    try {

        fileReader = BufferedReader(InputStreamReader(ClassPathResource("csv/SwissFoodCompData-v5.3.csv").inputStream, "UTF-8"))

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

        return  foods
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

