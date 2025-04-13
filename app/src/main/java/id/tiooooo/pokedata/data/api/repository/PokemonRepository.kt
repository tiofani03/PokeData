package id.tiooooo.pokedata.data.api.repository

import androidx.paging.PagingData
import id.tiooooo.pokedata.data.api.model.PokemonDetail
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.utils.wrapper.ResultState
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemon(): Flow<List<PokemonItem>>
    fun getPokemonFlow(query: String? = null): Flow<PagingData<PokemonItem>>
    fun getPagedPokemon(query: String): Flow<PagingData<PokemonItem>>
    suspend fun refreshFromNetwork(shouldRefresh: Boolean = false)
    suspend fun updatePokemonBackgroundColor(color: String, id: Int)
    fun getPokemonDetail(pokemonItem: PokemonItem): Flow<ResultState<PokemonDetail>>
}