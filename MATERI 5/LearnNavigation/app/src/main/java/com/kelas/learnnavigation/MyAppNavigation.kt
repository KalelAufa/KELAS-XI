package com.kelas.learnnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_A
    ) {
        // Rute untuk Screen A
        composable(route = Routes.SCREEN_A) {
            ScreenA(navController = navController)
        }
        
        // Rute untuk Screen B dengan argumen name
        composable(route = "${Routes.SCREEN_B}/{name}") { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name")
            ScreenB(name = name, navController = navController)
        }
    }
}