package id.tiooooo.pokedata.data.api.repository

interface UserRepository {
    suspend fun isLogin() : Boolean
}