package com.kelas.realtimewheater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kelas.realtimewheater.api.NetworkResponse
import com.kelas.realtimewheater.viewmodel.WeatherViewModel

@Composable
fun WeatherPage(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Search for any location") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (city.isNotBlank()) {
                            viewModel.getData(city)
                            keyboardController?.hide()
                        }
                    }
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(
                onClick = {
                    if (city.isNotBlank()) {
                        viewModel.getData(city)
                        keyboardController?.hide()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        // Weather display
        when (val result = weatherResult.value) {
            is NetworkResponse.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
            is NetworkResponse.Error -> {
                Text(
                    text = result.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            null -> {
                // Initial state - show nothing or a welcome message
                Text(
                    text = "Search for a city to get weather information",
                    modifier = Modifier.padding(top = 32.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
