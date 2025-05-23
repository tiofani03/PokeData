package id.tiooooo.pokedata.data.implementation.repository

import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.data.implementation.local.dao.UserDao
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity
import id.tiooooo.pokedata.utils.encryptor.PasswordEncryptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID

class UserRepositoryImpl(
    private val appDatastore: AppDatastore,
    private val userDao: UserDao,
    private val passwordEncryptor: PasswordEncryptor,
) : UserRepository {

    override suspend fun isLogin(): Boolean {
        return appDatastore.isLoggedInFlow.first()
    }

    override suspend fun executeLogin(email: String, password: String) = flow {
        val encrypted = passwordEncryptor.encrypt(password)
        val user = userDao.login(email.trim(), encrypted)
        if (user != null) {
            appDatastore.setLoginStatus(user.uuid, true)
            emit(true)
        } else {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun executeLogout() = flow {
        appDatastore.setLogout()
        emit(true)
    }.flowOn(Dispatchers.IO)

    override suspend fun executeRegister(
        email: String,
        password: String,
        name: String
    ): Flow<Boolean> = flow {
        val encryptedPassword = passwordEncryptor.encrypt(password)

        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) {
            emit(false)
            return@flow
        }

        val newUUID = UUID.randomUUID().toString()
        val newUser = UserEntity(
            uuid = newUUID,
            email = email.trim(),
            username = name.trim(),
            password = encryptedPassword
        )

        userDao.insertUser(newUser)
        appDatastore.setLoginStatus(newUUID, true)

        emit(true)
    }.flowOn(Dispatchers.IO)

    override suspend fun executeGetProfile(): Flow<UserEntity> = flow {
        val userUuid = appDatastore.userUuid.first()
        val data = userDao.getUserByUuid(userUuid)
        if (data != null) {
            emit(data)
        } else {
            emit(
                UserEntity(
                    id = -1,
                    email = "",
                    username = "",
                    password = "",
                    uuid = ""
                )
            )
        }
    }.flowOn(Dispatchers.IO)


}