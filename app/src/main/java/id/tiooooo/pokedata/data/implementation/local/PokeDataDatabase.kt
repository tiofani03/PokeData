package id.tiooooo.pokedata.data.implementation.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.tiooooo.pokedata.data.implementation.local.dao.UserDao
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class PokeDataDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}