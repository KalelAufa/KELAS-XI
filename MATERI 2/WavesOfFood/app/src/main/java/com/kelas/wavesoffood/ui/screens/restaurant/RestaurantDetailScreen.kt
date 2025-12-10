package com.kelas.wavesoffood.ui.screens.restaurant

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kelas.wavesoffood.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(
    restaurantId: String,
    onNavigateBack: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Menu", "Reviews", "Info")
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Restaurant Header with Image
        item {
            RestaurantHeader(
                onNavigateBack = onNavigateBack,
                isFavorite = isFavorite,
                onFavoriteClick = { isFavorite = !isFavorite }
            )
        }
        
        // Restaurant Info
        item {
            RestaurantInfo()
        }
        
        // Tab Navigation
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
        }
        
        // Tab Content
        item {
            when (selectedTab) {
                0 -> MenuSection()
                1 -> ReviewsSection()
                2 -> InfoSection()
            }
        }
    }
}

@Composable
private fun RestaurantHeader(
    onNavigateBack: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800",
            contentDescription = "Restaurant Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .background(
                        Color.White.copy(alpha = 0.9f),
                        CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .background(
                        Color.White.copy(alpha = 0.9f),
                        CircleShape
                    )
            ) {
                Icon(
                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Composable
private fun RestaurantInfo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Burger King",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "4.5",
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = " • 30-45 min • Free delivery",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = "Fast food • American • Burgers",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun MenuSection() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Popular Items",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        repeat(5) { index ->
            MenuItemCard(
                name = "Whopper Burger ${index + 1}",
                description = "Delicious burger with fresh ingredients",
                price = "Rp ${(45000 + index * 5000)}"
            )
        }
    }
}

@Composable
private fun MenuItemCard(
    name: String,
    description: String,
    price: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=300",
                contentDescription = name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ReviewsSection() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Customer Reviews",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        repeat(3) { index ->
            ReviewCard(
                customerName = "Customer ${index + 1}",
                rating = 4 + index % 2,
                comment = "Great food and excellent service!",
                date = "2 days ago"
            )
        }
    }
}

@Composable
private fun ReviewCard(
    customerName: String,
    rating: Int,
    comment: String,
    date: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = customerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < rating) Color(0xFFFFB800) else Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Text(
                text = comment,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun InfoSection() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Restaurant Information",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        InfoItem(
            title = "Address",
            content = "Jl. Sudirman No. 123, Jakarta Pusat"
        )
        
        InfoItem(
            title = "Phone",
            content = "+62 21 1234 5678"
        )
        
        InfoItem(
            title = "Opening Hours",
            content = "Monday - Sunday: 10:00 AM - 10:00 PM"
        )
        
        InfoItem(
            title = "Delivery Fee",
            content = "Free delivery for orders above Rp 50,000"
        )
    }
}

@Composable
private fun InfoItem(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
