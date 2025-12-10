package com.kelas.wavesoffood.ui.screens.food

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoodDetailScreen(
    foodId: String,
    onBack: () -> Unit,
    onAddToCart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Food Detail",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Food ID: $foodId",
            fontSize = 16.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onAddToCart) {
            Text("Add to Cart")
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
