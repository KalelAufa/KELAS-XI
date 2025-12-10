package com.kelas.wavesoffood.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kelas.wavesoffood.data.model.*
import com.kelas.wavesoffood.ui.theme.*

// Restaurant Card Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showFavoriteButton: Boolean = true
) {
    var isFavorite by remember { mutableStateOf(false) }
    
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 6.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            // Restaurant Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(restaurant.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = restaurant.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
                
                // Status indicators
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Featured badge
                    if (restaurant.isFeatured) {
                        Surface(
                            color = Orange,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Featured",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextOnPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }
                    
                    // Favorite button
                    if (showFavoriteButton) {
                        Surface(
                            color = Surface.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            IconButton(
                                onClick = { isFavorite = !isFavorite },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) Red else TextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
                
                // Delivery time badge
                Surface(
                    color = GreenPrimary,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = restaurant.deliveryTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextOnPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            // Restaurant Info
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = restaurant.cuisine,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = StarYellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${restaurant.rating}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = " (${restaurant.reviewCount})",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }
                    
                    // Delivery fee
                    Text(
                        text = if (restaurant.deliveryFee == 0.0) "Free delivery" else "$${restaurant.deliveryFee}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = if (restaurant.deliveryFee == 0.0) SuccessGreen else TextPrimary
                    )
                }
            }
        }
    }
}

// Food Item Card Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemCard(
    foodItem: FoodItem,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
    showAddButton: Boolean = true
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            // Food Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(foodItem.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = foodItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Food Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = foodItem.name,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        
                        if (foodItem.isPopular) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = "Popular",
                                tint = Orange,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = foodItem.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Price and rating
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$${foodItem.price}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = GreenPrimary
                        )
                        
                        if (foodItem.rating != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = StarYellow,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = " ${foodItem.rating}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                
                if (showAddButton) {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Add",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}
