package com.kelas.wavesoffood.navigation

sealed class Screen(val route: String) {
    // Auth Flow
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    
    // Main Flow
    object Main : Screen("main")
    object Home : Screen("home")
    object Search : Screen("search")
    object Cart : Screen("cart")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
    
    // Restaurant & Food
    object RestaurantDetail : Screen("restaurant_detail/{restaurantId}") {
        fun createRoute(restaurantId: String) = "restaurant_detail/$restaurantId"
    }
    object FoodDetail : Screen("food_detail/{foodId}") {
        fun createRoute(foodId: String) = "food_detail/$foodId"
    }
    
    // Checkout Flow
    object Checkout : Screen("checkout")
    object Payment : Screen("payment")
    object OrderTracking : Screen("order_tracking/{orderId}") {
        fun createRoute(orderId: String) = "order_tracking/$orderId"
    }
    
    // Profile Related
    object EditProfile : Screen("edit_profile")
    object AddressManagement : Screen("address_management")
    object AddAddress : Screen("add_address")
    object PaymentMethods : Screen("payment_methods")
    object OrderHistory : Screen("order_history")
    object Notifications : Screen("notifications")
    object Settings : Screen("settings")
    object Help : Screen("help")
}

// Bottom Navigation Items  
sealed class BottomNavItem(val route: String, val title: String) {
    object Home : BottomNavItem(Screen.Home.route, "Home")
    object Search : BottomNavItem(Screen.Search.route, "Search")
    object Cart : BottomNavItem(Screen.Cart.route, "Cart")
    object Orders : BottomNavItem(Screen.Orders.route, "Orders")
    object Profile : BottomNavItem(Screen.Profile.route, "Profile")
}
