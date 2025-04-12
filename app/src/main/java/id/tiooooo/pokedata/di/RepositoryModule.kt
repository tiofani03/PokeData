package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.data.implementation.repository.PokemonRepository
import id.tiooooo.pokedata.data.implementation.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { PokemonRepository(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}