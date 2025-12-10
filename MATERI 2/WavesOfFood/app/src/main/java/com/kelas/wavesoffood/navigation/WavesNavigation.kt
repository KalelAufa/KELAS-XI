package com.kelas.wavesoffood.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.kelas.wavesoffood.ui.screens.auth.LoginScreen
import com.kelas.wavesoffood.ui.screens.auth.RegisterScreen
import com.kelas.wavesoffood.ui.screens.auth.SplashScreen
import com.kelas.wavesoffood.ui.screens.auth.OnboardingScreen
import com.kelas.wavesoffood.ui.screens.main.MainScreen
import com.kelas.wavesoffood.ui.screens.home.HomeScreen
import com.kelas.wavesoffood.ui.screens.search.SearchScreen
import com.kelas.wavesoffood.ui.screens.cart.CartScreen
import com.kelas.wavesoffood.ui.screens.orders.OrdersScreen
import com.kelas.wavesoffood.ui.screens.profile.ProfileScreen
import com.kelas.wavesoffood.ui.screens.restaurant.RestaurantDetailScreen
import com.kelas.wavesoffood.ui.screens.food.FoodDetailScreen
import com.kelas.wavesoffood.ui.screens.checkout.CheckoutScreen
import com.kelas.wavesoffood.ui.screens.payment.PaymentScreen
import com.kelas.wavesoffood.ui.screens.tracking.OrderTrackingScreen

@Composable
fun WavesNavigation(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth Flow
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Main Flow
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToRestaurant = { restaurantId ->
                    navController.navigate(Screen.RestaurantDetail.createRoute(restaurantId))
                },
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateToRestaurant = { restaurantId ->
                    navController.navigate(Screen.RestaurantDetail.createRoute(restaurantId))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Cart.route) {
            CartScreen(
                onNavigateToCheckout = {
                    navController.navigate(Screen.Checkout.route)
                },
                onNavigateToRestaurant = { restaurantId ->
                    navController.navigate(Screen.RestaurantDetail.createRoute(restaurantId))
                }
            )
        }
        
        composable(Screen.Orders.route) {
            OrdersScreen(
                onNavigateToTracking = { orderId ->
                    navController.navigate(Screen.OrderTracking.createRoute(orderId))
                }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToEditProfile = {
                    navController.navigate(Screen.EditProfile.route)
                },
                onNavigateToAddresses = {
                    navController.navigate(Screen.AddressManagement.route)
                },
                onNavigateToPaymentMethods = {
                    navController.navigate(Screen.PaymentMethods.route)
                },
                onNavigateToOrderHistory = {
                    navController.navigate(Screen.OrderHistory.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToHelp = {
                    navController.navigate(Screen.Help.route)
                }
            )
        }
        
        // Restaurant & Food Detail
        composable(
            route = Screen.RestaurantDetail.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId") ?: ""
            RestaurantDetailScreen(
                restaurantId = restaurantId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.FoodDetail.route,
            arguments = listOf(navArgument("foodId") { type = NavType.StringType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
            FoodDetailScreen(
                foodId = foodId,
                onBack = {
                    navController.popBackStack()
                },
                onAddToCart = {
                    navController.popBackStack()
                }
            )
        }
        
        // Checkout Flow
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onNavigateToPayment = {
                    navController.navigate(Screen.Payment.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Payment.route) {
            PaymentScreen(
                onPaymentSuccess = { orderId ->
                    navController.navigate(Screen.OrderTracking.createRoute(orderId)) {
                        popUpTo(Screen.Main.route)
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.OrderTracking.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderTrackingScreen(
                orderId = orderId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
