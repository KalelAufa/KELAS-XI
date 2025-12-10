package com.kelas.wavesoffood.navigation

// Navigation Routes
object Routes {
    // Authentication Flow
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOT_PASSWORD = "forgot_password"
    
    // Main App Flow
    const val MAIN = "main"
    const val HOME = "home"
    const val SEARCH = "search"
    const val CART = "cart"
    const val ORDERS = "orders"
    const val PROFILE = "profile"
    
    // Restaurant & Food
    const val RESTAURANT_DETAIL = "restaurant_detail/{restaurantId}"
    const val FOOD_DETAIL = "food_detail/{foodItemId}"
    const val MENU_CATEGORY = "menu_category/{restaurantId}/{category}"
    
    // Checkout Flow
    const val CHECKOUT = "checkout"
    const val PAYMENT = "payment"
    const val ORDER_CONFIRMATION = "order_confirmation/{orderId}"
    const val ORDER_TRACKING = "order_tracking/{orderId}"
    
    // Profile & Settings
    const val EDIT_PROFILE = "edit_profile"
    const val ADDRESSES = "addresses"
    const val ADD_ADDRESS = "add_address"
    const val EDIT_ADDRESS = "edit_address/{addressId}"
    const val PAYMENT_METHODS = "payment_methods"
    const val ADD_PAYMENT_METHOD = "add_payment_method"
    const val NOTIFICATIONS = "notifications"
    const val SETTINGS = "settings"
    const val HELP_SUPPORT = "help_support"
    const val ORDER_HISTORY = "order_history"
    const val FAVORITES = "favorites"
    
    // Helper functions to create routes with parameters
    fun restaurantDetail(restaurantId: String) = "restaurant_detail/$restaurantId"
    fun foodDetail(foodItemId: String) = "food_detail/$foodItemId"
    fun menuCategory(restaurantId: String, category: String) = "menu_category/$restaurantId/$category"
    fun orderConfirmation(orderId: String) = "order_confirmation/$orderId"
    fun orderTracking(orderId: String) = "order_tracking/$orderId"
    fun editAddress(addressId: String) = "edit_address/$addressId"
}
