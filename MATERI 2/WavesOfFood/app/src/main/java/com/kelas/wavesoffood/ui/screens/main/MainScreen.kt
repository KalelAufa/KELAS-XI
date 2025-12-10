package com.kelas.wavesoffood.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kelas.wavesoffood.navigation.Screen
import com.kelas.wavesoffood.ui.screens.home.HomeScreen
import com.kelas.wavesoffood.ui.screens.search.SearchScreen
import com.kelas.wavesoffood.ui.screens.cart.CartScreen
import com.kelas.wavesoffood.ui.screens.orders.OrdersScreen
import com.kelas.wavesoffood.ui.screens.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home),
                    BottomNavItem(Screen.Search.route, "Search", Icons.Default.Search),
                    BottomNavItem(Screen.Cart.route, "Cart", Icons.Default.ShoppingCart),
                    BottomNavItem(Screen.Orders.route, "Orders", Icons.Default.Receipt),
                    BottomNavItem(Screen.Profile.route, "Profile", Icons.Default.Person)
                )
                
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToRestaurant = { restaurantId ->
                        navController.navigate("restaurant_detail/$restaurantId")
                    },
                    onNavigateToSearch = {
                        bottomNavController.navigate(Screen.Search.route)
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    onNavigateToRestaurant = { restaurantId ->
                        navController.navigate("restaurant_detail/$restaurantId")
                    },
                    onBack = {
                        bottomNavController.popBackStack()
                    }
                )
            }
            composable(Screen.Cart.route) {
                CartScreen(
                    onNavigateToCheckout = {
                        navController.navigate(Screen.Checkout.route)
                    },
                    onNavigateToRestaurant = { restaurantId ->
                        navController.navigate("restaurant_detail/$restaurantId")
                    }
                )
            }
            composable(Screen.Orders.route) {
                OrdersScreen(
                    onNavigateToTracking = { orderId ->
                        navController.navigate("order_tracking/$orderId")
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
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
