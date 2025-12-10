package com.kelas.realtimewheater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kelas.realtimewheater.api.WeatherModel

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Location name
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = data.location.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = data.location.country,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Temperature
        Text(
            text = "${data.current.tempC}°C",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        // Weather icon
        AsyncImage(
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Weather icon",
            modifier = Modifier.size(120.dp)
        )
        
        // Weather condition
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Weather details card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Row 1: Humidity and Wind Speed
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDetailItem(
                        label = "Humidity",
                        value = "${data.current.humidity}%"
                    )
                    WeatherDetailItem(
                        label = "Wind Speed",
                        value = "${data.current.windKph} km/h"
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Row 2: UV and Pressure
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDetailItem(
                        label = "UV",
                        value = data.current.uv
                    )
                    WeatherDetailItem(
                        label = "Pressure",
                        value = "${data.current.pressureMb} mb"
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Row 3: Feels Like and Visibility
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDetailItem(
                        label = "Feels Like",
                        value = "${data.current.feelslikeC}°C"
                    )
                    WeatherDetailItem(
                        label = "Visibility",
                        value = "${data.current.visKm} km"
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Local time
                WeatherDetailItem(
                    label = "Local Time",
                    value = data.location.localtime,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun WeatherDetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
