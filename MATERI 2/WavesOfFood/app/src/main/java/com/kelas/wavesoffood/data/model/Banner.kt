package com.kelas.wavesoffood.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "banners")
data class Banner(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val actionType: String, // "restaurant", "category", "external"
    val actionValue: String?, // restaurant_id, category_name, url
    val isActive: Boolean = true,
    val priority: Int = 0,
    val startDate: Long? = null,
    val endDate: Long? = null
) : Parcelable
