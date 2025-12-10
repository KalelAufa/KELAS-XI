package com.kelas.wavesoffood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val id: String,
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImageUrl: String,
    val items: List<OrderItem>,
    val status: OrderStatus,
    val deliveryAddress: Address,
    val paymentMethod: PaymentMethod,
    val subtotal: Double,
    val deliveryFee: Double,
    val tax: Double,
    val discount: Double = 0.0,
    val totalAmount: Double,
    val orderTime: Long,
    val estimatedDeliveryTime: Long? = null,
    val actualDeliveryTime: Long? = null,
    val specialInstructions: String? = null,
    val deliveryPersonName: String? = null,
    val deliveryPersonPhone: String? = null,
    val rating: Float? = null,
    val review: String? = null
) : Parcelable

@Parcelize
data class OrderItem(
    val foodItemId: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val quantity: Int,
    val selectedSize: FoodSize? = null,
    val selectedAddOns: List<AddOn> = emptyList(),
    val specialInstructions: String? = null
) : Parcelable

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARING,
    READY_FOR_PICKUP,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

@Parcelize
data class PaymentMethod(
    val id: String,
    val type: PaymentType,
    val name: String, // "Visa ****1234", "GoPay", "Cash"
    val isDefault: Boolean = false
) : Parcelable

enum class PaymentType {
    CREDIT_CARD,
    DEBIT_CARD,
    DIGITAL_WALLET,
    CASH_ON_DELIVERY
}
