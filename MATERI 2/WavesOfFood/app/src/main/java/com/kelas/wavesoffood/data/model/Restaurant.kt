package com.kelas.wavesoffood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val bannerImageUrl: String? = null,
    val rating: Float,
    val reviewCount: Int,
    val deliveryTime: String, // "25-35 min"
    val deliveryFee: Double,
    val minimumOrder: Double,
    val categories: List<String> = emptyList(),
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val isOpen: Boolean = true,
    val isFeatured: Boolean = false,
    val tags: List<String> = emptyList(), // "Fast delivery", "Popular", etc.
    val priceRange: PriceRange = PriceRange.MEDIUM,
    val cuisine: String, // "Italian", "Asian", etc.
    val openingHours: OpeningHours? = null
) : Parcelable

@Parcelize
data class OpeningHours(
    val monday: String,
    val tuesday: String,
    val wednesday: String,
    val thursday: String,
    val friday: String,
    val saturday: String,
    val sunday: String
) : Parcelable

enum class PriceRange {
    LOW,     // $
    MEDIUM,  // $$
    HIGH,    // $$$
    PREMIUM  // $$$$
}
