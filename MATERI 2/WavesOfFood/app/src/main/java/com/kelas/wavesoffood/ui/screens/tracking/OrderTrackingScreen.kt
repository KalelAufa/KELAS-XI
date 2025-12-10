package com.kelas.wavesoffood.ui.screens.tracking

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderTrackingScreen(
    orderId: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Order Tracking",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Order ID: $orderId",
            fontSize = 16.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Your order is being prepared...",
            fontSize = 14.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onBack) {
            Text("Back to Orders")
        }
    }
}
