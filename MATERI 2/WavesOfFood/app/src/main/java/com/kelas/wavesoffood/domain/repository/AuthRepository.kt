package com.kelas.wavesoffood.domain.repository

import com.kelas.wavesoffood.data.model.User
import com.kelas.wavesoffood.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<User>>
    suspend fun register(name: String, email: String, password: String, phone: String): Flow<Resource<User>>
    suspend fun logout(): Flow<Resource<Unit>>
    suspend fun getCurrentUser(): Flow<Resource<User?>>
}
