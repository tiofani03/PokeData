package id.tiooooo.pokedata.data.implementation.repository

import id.tiooooo.pokedata.data.implementation.remote.service.PokeService
import id.tiooooo.pokedata.data.implementation.remote.service.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRepository(
    private val pokeService: PokeService,
) {
    fun getPokemon(): Flow<List<PokemonResponse>> = flow {
        try {
            val response = pokeService.getPokemon()
            emit(response.data.orEmpty())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}