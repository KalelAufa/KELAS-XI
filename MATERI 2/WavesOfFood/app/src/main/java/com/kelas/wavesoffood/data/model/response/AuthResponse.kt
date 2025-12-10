package com.kelas.wavesoffood.data.model.response

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData?
)

data class AuthData(
    val token: String,
    val refreshToken: String,
    val user: UserInfo
)

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String?
)
