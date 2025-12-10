package com.kelas.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelas.stateexample.ui.theme.StateExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StateExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyScreenWithViewModel(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Tugas 1: Menggunakan remember untuk state lokal
@Composable
fun MyScreen(modifier: Modifier = Modifier) {
    // Deklarasi state lokal menggunakan remember dan mutableStateOf
    var name by remember { mutableStateOf("") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Menampilkan text dengan nilai dari state
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // OutlinedTextField untuk input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Tugas 2: Menggunakan rememberSaveable untuk state yang bertahan saat configuration change
@Composable
fun MyScreenWithSaveable(modifier: Modifier = Modifier) {
    // Deklarasi state menggunakan rememberSaveable - akan bertahan saat rotasi layar
    var name by rememberSaveable { mutableStateOf("") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Menampilkan text dengan nilai dari state
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "State akan bertahan saat rotasi layar",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // OutlinedTextField untuk input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Tugas 3 & 4: Menggunakan ViewModel untuk state hoisting
@Composable
fun MyScreenWithViewModel(
    modifier: Modifier = Modifier,
    viewModel: StateTestViewModel = viewModel()
) {
    // Menggunakan observeAsState untuk mengamati LiveData dari ViewModel
    val name by viewModel.name.observeAsState(initial = "")
    val surname by viewModel.surname.observeAsState(initial = "")
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Menampilkan nama lengkap yang dibentuk dari name dan surname
        Text(
            text = "Hello ${viewModel.getFullName()}!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "State dikelola oleh ViewModel",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // OutlinedTextField untuk input name
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.onNameUpdate(it) },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        
        // OutlinedTextField untuk input surname
        OutlinedTextField(
            value = surname,
            onValueChange = { viewModel.onSurnameUpdate(it) },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Menampilkan informasi tambahan
        Text(
            text = "First Name: $name",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 16.dp)
        )
        
        Text(
            text = "Last Name: $surname",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MyScreenWithViewModelPreview() {
    StateExampleTheme {
        MyScreenWithViewModel()
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenWithSaveablePreview() {
    StateExampleTheme {
        MyScreenWithSaveable()
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    StateExampleTheme {
        MyScreen()
    }
}