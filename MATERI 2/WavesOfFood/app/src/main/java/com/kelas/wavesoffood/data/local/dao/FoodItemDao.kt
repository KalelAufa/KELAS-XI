package com.kelas.wavesoffood.data.local.dao

import androidx.room.*
import com.kelas.wavesoffood.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {
    
    @Query("SELECT * FROM food_items WHERE restaurantId = :restaurantId")
    fun getFoodItemsByRestaurant(restaurantId: String): Flow<List<FoodItem>>
    
    @Query("SELECT * FROM food_items WHERE id = :foodItemId")
    suspend fun getFoodItemById(foodItemId: String): FoodItem?
    
    @Query("SELECT * FROM food_items WHERE restaurantId = :restaurantId AND category = :category")
    fun getFoodItemsByCategory(restaurantId: String, category: String): Flow<List<FoodItem>>
    
    @Query("SELECT * FROM food_items WHERE isPopular = 1")
    fun getPopularFoodItems(): Flow<List<FoodItem>>
    
    @Query("SELECT * FROM food_items WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchFoodItems(query: String): Flow<List<FoodItem>>
    
    @Query("SELECT DISTINCT category FROM food_items WHERE restaurantId = :restaurantId")
    suspend fun getCategoriesByRestaurant(restaurantId: String): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItem(foodItem: FoodItem)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItems(foodItems: List<FoodItem>)
    
    @Update
    suspend fun updateFoodItem(foodItem: FoodItem)
    
    @Delete
    suspend fun deleteFoodItem(foodItem: FoodItem)
    
    @Query("DELETE FROM food_items")
    suspend fun deleteAllFoodItems()
}
