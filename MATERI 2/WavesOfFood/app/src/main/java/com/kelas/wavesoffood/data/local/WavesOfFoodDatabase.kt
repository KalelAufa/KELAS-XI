package com.kelas.wavesoffood.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.kelas.wavesoffood.data.local.converter.Converters
import com.kelas.wavesoffood.data.local.dao.*
import com.kelas.wavesoffood.data.model.*

@Database(
    entities = [
        User::class,
        Restaurant::class,
        FoodItem::class,
        CartItem::class,
        Order::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WavesOfFoodDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun foodItemDao(): FoodItemDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}
