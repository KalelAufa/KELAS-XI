package com.kelas.wavesoffood.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit,
    onNavigateToAddresses: () -> Unit,
    onNavigateToPaymentMethods: () -> Unit,
    onNavigateToOrderHistory: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToHelp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Profile Screen",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onNavigateToEditProfile) {
            Text("Edit Profile")
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(onClick = onNavigateToOrderHistory) {
            Text("Order History")
        }
    }
}
