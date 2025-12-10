package com.kelas.learnnavigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ScreenA(navController: NavHostController) {
    var nameInput by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Screen A",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Masukkan nama Anda") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        )
        
        Button(
            onClick = {
                // Navigasi ke Screen B dengan mengirim nama yang diinput pengguna
                val name = if (nameInput.isNotBlank()) nameInput else "Pengunjung"
                navController.navigate("${Routes.SCREEN_B}/$name")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Go to Screen B")
        }
    }
}