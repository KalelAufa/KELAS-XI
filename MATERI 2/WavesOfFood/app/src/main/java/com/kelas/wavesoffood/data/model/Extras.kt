package com.kelas.wavesoffood.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class BannerActionType {
    RESTAURANT,
    CATEGORY,
    EXTERNAL_URL,
    NONE
}

@Parcelize
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val imageUrl: String? = null,
    val type: NotificationType,
    val isRead: Boolean = false,
    val timestamp: Long,
    val actionData: String? = null
) : Parcelable

enum class NotificationType {
    ORDER_UPDATE,
    PROMOTION,
    GENERAL,
    DELIVERY_ALERT
}

@Parcelize
data class Review(
    val id: String,
    val userId: String,
    val userName: String,
    val userImageUrl: String? = null,
    val rating: Float,
    val comment: String,
    val timestamp: Long,
    val restaurantReply: String? = null,
    val replyTimestamp: Long? = null,
    val images: List<String> = emptyList()
) : Parcelable

@Parcelize
data class PromoCode(
    val id: String,
    val code: String,
    val title: String,
    val description: String,
    val discountType: DiscountType,
    val discountValue: Double, // percentage or fixed amount
    val minimumOrder: Double,
    val maximumDiscount: Double? = null,
    val expiryDate: Long,
    val usageLimit: Int? = null,
    val usedCount: Int = 0,
    val isActive: Boolean = true
) : Parcelable

enum class DiscountType {
    PERCENTAGE,
    FIXED_AMOUNT
}
