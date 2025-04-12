package id.tiooooo.pokedata.data.implementation.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM user WHERE uuid = :uuid")
    suspend fun getUserByUuid(uuid: String): UserEntity?

    @Query("SELECT * FROM user")
    suspend fun executeGetAllUser(): List<UserEntity?>?
}