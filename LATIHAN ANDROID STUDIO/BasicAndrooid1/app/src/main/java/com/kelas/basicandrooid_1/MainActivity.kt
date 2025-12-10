package com.kelas.basicandrooid_1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelas.basicandrooid_1.ui.theme.BasicAndrooid1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicAndrooid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormComponent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FormComponent(modifier: Modifier = Modifier) {
    var nama by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Google
        Image(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Logo",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 32.dp)
        )
        
        // Text "Nama"
        Text(
            text = "Nama",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        
        // Text Field untuk Nama
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Masukkan nama Anda") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        
        // Text "Alamat"
        Text(
            text = "Alamat",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        
        // Text Field untuk Alamat
        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text("Masukkan alamat Anda") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )
        
        // Button Simpan
        Button(
            onClick = { 
                // Menampilkan toast dengan isi nama dan alamat
                val message = if (nama.isNotBlank() && alamat.isNotBlank()) {
                    "Nama: $nama\nAlamat: $alamat"
                } else if (nama.isNotBlank()) {
                    "Nama: $nama\nAlamat: (belum diisi)"
                } else if (alamat.isNotBlank()) {
                    "Nama: (belum diisi)\nAlamat: $alamat"
                } else {
                    "Nama dan alamat belum diisi"
                }
                
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Simpan",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormComponentPreview() {
    BasicAndrooid1Theme {
        FormComponent()
    }
}