package com.kelas.learnnavigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ScreenB(name: String?, navController: NavHostController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Screen B",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Selamat datang, ${name ?: "Pengunjung"}!",
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
        
        navController?.let { controller ->
            Button(
                onClick = {
                    controller.popBackStack()
                },
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text("Kembali ke Screen A")
            }
        }
    }
}