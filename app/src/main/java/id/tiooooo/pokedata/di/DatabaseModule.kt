package id.tiooooo.pokedata.di

import androidx.room.Room
import id.tiooooo.pokedata.data.implementation.local.PokeDataDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), PokeDataDatabase::class.java, "poke_data.db")
            .fallbackToDestructiveMigration(false)
            .build()
    }
    single { get<PokeDataDatabase>().userDao() }
}