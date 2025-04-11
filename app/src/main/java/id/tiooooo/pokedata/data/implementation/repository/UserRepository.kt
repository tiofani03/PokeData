package id.tiooooo.pokedata.data.implementation.repository

import id.tiooooo.pokedata.data.implementation.local.dao.UserDao
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity

class UserRepository(
    private val userDao: UserDao,
) {
    suspend fun register(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }

    suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getUsers(): List<UserEntity?>? {
        return userDao.executeGetAllUser()
    }
}