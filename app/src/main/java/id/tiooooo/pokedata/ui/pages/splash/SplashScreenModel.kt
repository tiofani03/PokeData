package id.tiooooo.pokedata.ui.pages.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity
import id.tiooooo.pokedata.data.implementation.remote.service.PokemonResponse
import id.tiooooo.pokedata.data.implementation.repository.PokemonRepository
import id.tiooooo.pokedata.data.implementation.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SplashScreenModel(
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository,
) : ScreenModel {
    private val _userList = MutableStateFlow<List<UserEntity>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _pokemonList = MutableStateFlow<List<PokemonResponse>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()

    init {
        executePokemon()
    }

    fun executeUsers() {
        screenModelScope.launch {
            val data = userRepository.getUsers()?.toNonNullList().orEmpty()
            _userList.value = data
        }
    }

    fun executePokemon() {
        screenModelScope.launch {
            pokemonRepository.getPokemon()
                .collect { _pokemonList.value = it }
        }
    }
}

fun List<UserEntity?>.toNonNullList(): List<UserEntity> {
    return this.filterNotNull()
}
