package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.data.api.repository.PokemonRepository
import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.data.implementation.repository.PokemonRepositoryImpl
import id.tiooooo.pokedata.data.implementation.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get(),get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}