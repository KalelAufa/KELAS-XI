package com.kelas.wavesoffood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val id: String,
    val foodItemId: String,
    val restaurantId: String,
    val name: String,
    val imageUrl: String,
    val basePrice: Double,
    val quantity: Int,
    val selectedSize: FoodSize? = null,
    val selectedAddOns: List<AddOn> = emptyList(),
    val specialInstructions: String? = null,
    val totalPrice: Double
) : Parcelable {
    fun calculateTotalPrice(): Double {
        val sizePrice = selectedSize?.price ?: basePrice
        val addOnsPrice = selectedAddOns.sumOf { it.price }
        return (sizePrice + addOnsPrice) * quantity
    }
}

@Parcelize
data class Cart(
    val items: List<CartItem> = emptyList(),
    val restaurantId: String? = null,
    val restaurantName: String? = null
) : Parcelable {
    
    val subtotal: Double
        get() = items.sumOf { it.totalPrice }
    
    val totalItems: Int
        get() = items.sumOf { it.quantity }
    
    val isEmpty: Boolean
        get() = items.isEmpty()
    
    fun canAddItem(newItem: CartItem): Boolean {
        return restaurantId == null || restaurantId == newItem.restaurantId
    }
}
