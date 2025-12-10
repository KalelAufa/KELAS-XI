package com.kelas.mycalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.kelas.mycalculator.ui.theme.MyCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val firstText = remember { mutableStateOf("") }
    val secondText = remember { mutableStateOf("") }

    fun parseInputs(): Pair<Double?, Double?> {
        val a = firstText.value.toDoubleOrNull()
        val b = secondText.value.toDoubleOrNull()
        return Pair(a, b)
    }

    fun showResult(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Container column to limit the height used by input fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = firstText.value,
                onValueChange = { firstText.value = it },
                label = { Text("Angka pertama") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = secondText.value,
                onValueChange = { secondText.value = it },
                label = { Text("Angka kedua") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
        }

        // Buttons row at the bottom of the input area
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {

            // Add button: pill shaped, primary color
            Button(
                onClick = {
                    val (a, b) = parseInputs()
                    if (a == null || b == null) {
                        showResult("Masukkan angka valid di kedua bidang")
                    } else {
                        showResult("Hasil: ${a + b}")
                    }
                },
                modifier = Modifier
                    .width(72.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "+",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Operation buttons: circular, elevated surface look
            val ops = listOf("-", "×", "÷")
            // Subtraction
            Surface(
                modifier = Modifier
                    .width(72.dp)
                    .height(52.dp)
                    .padding(horizontal = 6.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                tonalElevation = 4.dp
            ) {
                Button(
                    onClick = {
                        val (a, b) = parseInputs()
                        if (a == null || b == null) {
                            showResult("Masukkan angka valid di kedua bidang")
                        } else {
                            showResult("Hasil: ${a - b}")
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "-",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Multiplication
            Surface(
                modifier = Modifier
                    .width(72.dp)
                    .height(52.dp)
                    .padding(horizontal = 6.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                tonalElevation = 4.dp
            ) {
                Button(
                    onClick = {
                        val (a, b) = parseInputs()
                        if (a == null || b == null) {
                            showResult("Masukkan angka valid di kedua bidang")
                        } else {
                            showResult("Hasil: ${a * b}")
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "×",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Division
            Surface(
                modifier = Modifier
                    .width(72.dp)
                    .height(52.dp)
                    .padding(horizontal = 6.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                tonalElevation = 4.dp
            ) {
                Button(
                    onClick = {
                        val (a, b) = parseInputs()
                        if (a == null || b == null) {
                            showResult("Masukkan angka valid di kedua bidang")
                        } else if (b == 0.0) {
                            showResult("Pembagian dengan nol tidak valid")
                        } else {
                            showResult("Hasil: ${a / b}")
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "÷",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    MyCalculatorTheme {
        Calculator()
    }
}