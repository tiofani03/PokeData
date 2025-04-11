package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.data.implementation.repository.PokemonRepository
import id.tiooooo.pokedata.data.implementation.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get()) }
    single { PokemonRepository(get()) }
}