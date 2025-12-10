package com.kelas.wavesoffood.data.remote.api

import com.kelas.wavesoffood.data.model.*
import com.kelas.wavesoffood.data.model.request.*
import com.kelas.wavesoffood.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface FoodApi {
    
    // Authentication
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>
    
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshRequest: RefreshTokenRequest): Response<AuthResponse>
    
    // User
    @GET("user/profile")
    suspend fun getUserProfile(): Response<User>
    
    @PUT("user/profile")
    suspend fun updateUserProfile(@Body user: User): Response<User>
    
    // Restaurants
    @GET("restaurants")
    suspend fun getRestaurants(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("category") category: String? = null,
        @Query("search") search: String? = null
    ): Response<RestaurantResponse>
    
    @GET("restaurants/featured")
    suspend fun getFeaturedRestaurants(): Response<List<Restaurant>>
    
    @GET("restaurants/{id}")
    suspend fun getRestaurantById(@Path("id") restaurantId: String): Response<Restaurant>
    
    @GET("restaurants/search")
    suspend fun searchRestaurants(@Query("q") query: String): Response<List<Restaurant>>
    
    // Food Items
    @GET("restaurants/{id}/menu")
    suspend fun getRestaurantMenu(@Path("id") restaurantId: String): Response<MenuResponse>
    
    @GET("food/{id}")
    suspend fun getFoodItemById(@Path("id") foodItemId: String): Response<FoodItem>
    
    @GET("food/search")
    suspend fun searchFoodItems(@Query("q") query: String): Response<FoodItemResponse>
    
    // Categories
    @GET("categories")
    suspend fun getCategories(): Response<List<FoodCategory>>
    
    // Banners
    @GET("banners")
    suspend fun getBanners(): Response<List<Banner>>
    
    // Cart & Orders
    @POST("orders")
    suspend fun createOrder(@Body orderRequest: CreateOrderRequest): Response<Order>
    
    @GET("orders")
    suspend fun getUserOrders(): Response<List<Order>>
    
    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") orderId: String): Response<Order>
    
    @PUT("orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") orderId: String,
        @Body statusUpdate: OrderStatusUpdate
    ): Response<Order>
    
    // Reviews
    @POST("restaurants/{id}/reviews")
    suspend fun addRestaurantReview(
        @Path("id") restaurantId: String,
        @Body review: ReviewRequest
    ): Response<Review>
    
    @GET("restaurants/{id}/reviews")
    suspend fun getRestaurantReviews(@Path("id") restaurantId: String): Response<List<Review>>
    
    // Promotions
    @GET("promotions")
    suspend fun getPromotions(): Response<List<PromoCode>>
    
    @POST("promotions/validate")
    suspend fun validatePromoCode(@Body promoRequest: PromoValidationRequest): Response<PromoValidationResponse>
}

// Request/Response models
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

data class AuthResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

data class RefreshTokenRequest(
    val refreshToken: String
)

data class RestaurantResponse(
    val restaurants: List<Restaurant>,
    val totalCount: Int,
    val currentPage: Int,
    val totalPages: Int
)

data class MenuResponse(
    val restaurant: Restaurant,
    val categories: List<String>,
    val foodItems: List<FoodItem>
)

data class FoodItemResponse(
    val foodItems: List<FoodItem>,
    val totalCount: Int
)

data class CreateOrderRequest(
    val restaurantId: String,
    val items: List<OrderItem>,
    val deliveryAddress: Address,
    val paymentMethodId: String,
    val specialInstructions: String? = null,
    val promoCode: String? = null
)

data class OrderStatusUpdate(
    val status: OrderStatus
)

data class ReviewRequest(
    val rating: Float,
    val comment: String,
    val orderId: String? = null
)

data class PromoValidationRequest(
    val code: String,
    val orderTotal: Double
)

data class PromoValidationResponse(
    val isValid: Boolean,
    val discount: Double,
    val message: String
)
