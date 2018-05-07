package ch.keepcalm.keepcalmfood.food

import org.springframework.data.mongodb.repository.MongoRepository

interface FoodRepository : MongoRepository<Food, String> {

}