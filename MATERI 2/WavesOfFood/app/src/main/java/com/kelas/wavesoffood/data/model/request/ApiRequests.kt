package com.kelas.wavesoffood.data.model.request

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)

data class RefreshTokenRequest(
    val refreshToken: String
)

data class CreateOrderRequest(
    val restaurantId: String,
    val items: List<OrderItemRequest>,
    val deliveryAddress: String,
    val paymentMethod: String,
    val totalAmount: Double
)

data class OrderItemRequest(
    val foodItemId: String,
    val quantity: Int,
    val extras: List<String> = emptyList(),
    val notes: String? = null
)

data class OrderStatusUpdate(
    val status: String
)
