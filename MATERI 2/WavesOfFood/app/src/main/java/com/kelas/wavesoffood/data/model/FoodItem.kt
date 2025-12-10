package com.kelas.wavesoffood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey
    val id: String,
    val restaurantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val imageUrls: List<String> = emptyList(), // Gallery images
    val category: String,
    val isVegetarian: Boolean = false,
    val isVegan: Boolean = false,
    val isSpicy: Boolean = false,
    val isPopular: Boolean = false,
    val rating: Float? = null,
    val reviewCount: Int = 0,
    val preparationTime: String? = null, // "15-20 min"
    val calories: Int? = null,
    val ingredients: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val sizes: List<FoodSize> = emptyList(),
    val addOns: List<AddOn> = emptyList(),
    val isAvailable: Boolean = true,
    val discountPercentage: Int? = null
) : Parcelable

@Parcelize
data class FoodSize(
    val id: String,
    val name: String, // "Small", "Medium", "Large"
    val price: Double,
    val isDefault: Boolean = false
) : Parcelable

@Parcelize
data class AddOn(
    val id: String,
    val name: String,
    val price: Double,
    val isRequired: Boolean = false,
    val maxQuantity: Int = 1
) : Parcelable

@Parcelize
data class FoodCategory(
    val id: String,
    val name: String,
    val imageUrl: String,
    val isPopular: Boolean = false
) : Parcelable
