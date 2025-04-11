package id.tiooooo.pokedata.di

import androidx.room.Room
import id.tiooooo.pokedata.data.implementation.local.PokeDataDatabase
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.utils.AppConstants.DATABASE_NAME
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(get(), PokeDataDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration(false)
            .build()
    }
    single { get<PokeDataDatabase>().userDao() }
    single { AppDatastore(get()) }
}