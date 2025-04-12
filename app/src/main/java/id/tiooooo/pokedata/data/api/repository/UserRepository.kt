package id.tiooooo.pokedata.data.api.repository

import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isLogin(): Boolean
    suspend fun executeLogin(email: String, password: String): Flow<Boolean>
    suspend fun executeLogout(): Flow<Boolean>
    suspend fun executeRegister(email: String, password: String, name: String): Flow<Boolean>
    suspend fun executeGetProfile(): Flow<UserEntity>
}