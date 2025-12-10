package com.kelas.wavesoffood.di

import android.content.Context
import androidx.room.Room
import com.kelas.wavesoffood.data.local.WavesOfFoodDatabase
import com.kelas.wavesoffood.data.local.dao.CartDao
import com.kelas.wavesoffood.data.local.dao.FoodItemDao
import com.kelas.wavesoffood.data.local.dao.OrderDao
import com.kelas.wavesoffood.data.local.dao.RestaurantDao
import com.kelas.wavesoffood.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WavesOfFoodDatabase {
        return Room.databaseBuilder(
            context,
            WavesOfFoodDatabase::class.java,
            "waves_of_food_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: WavesOfFoodDatabase): UserDao = database.userDao()

    @Provides
    fun provideRestaurantDao(database: WavesOfFoodDatabase): RestaurantDao = database.restaurantDao()

    @Provides
    fun provideFoodItemDao(database: WavesOfFoodDatabase): FoodItemDao = database.foodItemDao()

    @Provides
    fun provideCartDao(database: WavesOfFoodDatabase): CartDao = database.cartDao()

    @Provides
    fun provideOrderDao(database: WavesOfFoodDatabase): OrderDao = database.orderDao()
}
