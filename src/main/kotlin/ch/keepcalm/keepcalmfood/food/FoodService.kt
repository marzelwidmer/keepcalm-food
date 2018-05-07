package ch.keepcalm.keepcalmfood.food

import org.springframework.stereotype.Service

@Service
class FoodService (val foodRepository: FoodRepository){


    fun findAllFoods (): MutableList<Food>? {
        return foodRepository.findAll()
    }


    fun loadDatabase (){
        val foods = cvsToFoodList()
        foods?.map { food -> foodRepository.save(food) }
    }

    fun findOne(id: String): Food {
        val food: Food = foodRepository.findById(id).get()
        return food
    }
}