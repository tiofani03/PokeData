package id.tiooooo.pokedata.data.implementation.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.tiooooo.pokedata.data.implementation.local.dao.PokemonDao
import id.tiooooo.pokedata.data.implementation.local.dao.UserDao
import id.tiooooo.pokedata.data.implementation.local.entity.PokemonItemEntity
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, PokemonItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokeDataDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao
}