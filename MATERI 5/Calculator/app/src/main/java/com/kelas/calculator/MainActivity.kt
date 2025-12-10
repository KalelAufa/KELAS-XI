package com.kelas.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelas.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = viewModel()
) {
    // Observe LiveData dari ViewModel
    val equationText by viewModel.equationText.observeAsState("")
    val resultText by viewModel.resultText.observeAsState("")
    
    // Definisi daftar tombol kalkulator (4 kolom x 5 baris)
    val buttonList = listOf(
        "AC", "C", "/", "*",
        "7", "8", "9", "-",
        "4", "5", "6", "+",
        "1", "2", "3", "=",
        "0", ".", "", ""
    )
    
    // Struktur tata letak utama
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E))
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Area Tampilan (Ekspresi dan Hasil)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Tampilan Ekspresi
            Text(
                text = equationText,
                fontSize = 32.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Tampilan Hasil
            Text(
                text = resultText,
                fontSize = 60.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
        
        // Spacer untuk mendorong tombol ke bawah
        Spacer(modifier = Modifier.weight(1f))
        
        // Grid Tombol Kalkulator
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(buttonList) { button ->
                if (button.isNotEmpty()) {
                    CalculatorButton(
                        button = button,
                        onClick = { viewModel.onButtonClick(button) }
                    )
                } else {
                    // Spacer untuk sel kosong
                    Box(modifier = Modifier.size(80.dp))
                }
            }
        }
    }
}

/**
 * Composable untuk tombol kalkulator individual
 * @param button Label tombol
 * @param onClick Callback yang dipanggil saat tombol diklik
 */
@Composable
fun CalculatorButton(
    button: String,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        shape = CircleShape,
        containerColor = getColor(button)
    ) {
        Text(
            text = button,
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Fungsi untuk mendapatkan warna tombol berdasarkan labelnya
 * @param button Label tombol
 * @return Warna untuk tombol
 */
fun getColor(button: String): Color {
    return when (button) {
        // AC dan C - Warna Merah
        "AC", "C" -> Color(0xFFD32F2F)
        
        // Operator - Warna Oranye
        "+", "-", "*", "/", "=" -> Color(0xFFFF9500)
        
        // Angka dan Titik - Warna Teal/Abu-abu gelap
        else -> Color(0xFF505050)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        Calculator()
    }
}
