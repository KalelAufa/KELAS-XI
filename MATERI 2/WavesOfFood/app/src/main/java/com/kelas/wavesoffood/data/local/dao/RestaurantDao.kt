package com.kelas.wavesoffood.data.local.dao

import androidx.room.*
import com.kelas.wavesoffood.data.model.Restaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    
    @Query("SELECT * FROM restaurants")
    suspend fun getAllRestaurants(): List<Restaurant>
    
    @Query("SELECT * FROM restaurants WHERE id = :restaurantId")
    suspend fun getRestaurantById(restaurantId: String): Restaurant?
    
    @Query("SELECT * FROM restaurants WHERE isFeatured = 1")
    suspend fun getFeaturedRestaurants(): List<Restaurant>
    
    @Query("SELECT * FROM restaurants WHERE name LIKE '%' || :query || '%' OR cuisine LIKE '%' || :query || '%'")
    suspend fun searchRestaurants(query: String): List<Restaurant>
    
    @Query("SELECT * FROM restaurants WHERE cuisine = :cuisine")
    suspend fun getRestaurantsByCategory(cuisine: String): List<Restaurant>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<Restaurant>)
    
    @Update
    suspend fun updateRestaurant(restaurant: Restaurant)
    
    @Delete
    suspend fun deleteRestaurant(restaurant: Restaurant)
    
    @Query("DELETE FROM restaurants")
    suspend fun deleteAllRestaurants()
}
