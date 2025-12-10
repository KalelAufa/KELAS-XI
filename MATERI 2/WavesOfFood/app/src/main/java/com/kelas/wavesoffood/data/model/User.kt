package com.kelas.wavesoffood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val phone: String? = null,
    val profileImageUrl: String? = null,
    val addresses: List<Address> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val isVerified: Boolean = false
) : Parcelable

@Parcelize
data class Address(
    val id: String,
    val title: String, // Home, Office, etc.
    val fullAddress: String,
    val latitude: Double,
    val longitude: Double,
    val instructions: String? = null,
    val isDefault: Boolean = false
) : Parcelable
