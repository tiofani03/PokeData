package id.tiooooo.pokedata.data.implementation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.tiooooo.pokedata.data.api.model.PokemonDetail
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.api.model.Stat
import id.tiooooo.pokedata.data.api.repository.PokemonRepository
import id.tiooooo.pokedata.data.implementation.local.dao.PokemonDao
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.data.implementation.local.entity.toPokemonItem
import id.tiooooo.pokedata.data.implementation.remote.pagingsource.PokemonPagingSource
import id.tiooooo.pokedata.data.implementation.remote.response.getEnglishDescription
import id.tiooooo.pokedata.data.implementation.remote.response.getPokemonIdFromUrl
import id.tiooooo.pokedata.data.implementation.remote.response.mapToEvolutionList
import id.tiooooo.pokedata.data.implementation.remote.response.toPokemonEntity
import id.tiooooo.pokedata.data.implementation.remote.response.toPokemonItem
import id.tiooooo.pokedata.data.implementation.remote.service.PokeService
import id.tiooooo.pokedata.utils.AppConstants.POKEMON_MAX_DATA
import id.tiooooo.pokedata.utils.wrapper.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val appDatastore: AppDatastore,
    private val pokeService: PokeService,
    private val pokeDao: PokemonDao,
) : PokemonRepository {
    override fun getPokemon(): Flow<List<PokemonItem>> = flow {
        try {
            val response = pokeService.getPokemon(10, 10)
            emit(response.data?.map { it.toPokemonItem() } ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getPokemonFlow(query: String?): Flow<PagingData<PokemonItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(pokeService, query) }
        ).flow
    }

    override fun getPagedPokemon(query: String): Flow<PagingData<PokemonItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                if (query.isBlank()) {
                    pokeDao.getPagedPokemon()
                } else {
                    pokeDao.searchPokemonPaged(query)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toPokemonItem()
            }
        }
    }

    override suspend fun refreshFromNetwork(shouldRefresh: Boolean) {
        if (shouldRefresh && pokeDao.countPokemon() == 0 && !appDatastore.isAlreadyLoaded.first()) {
            val response = pokeService.getPokemon(limit = POKEMON_MAX_DATA, offset = 0)
            val list = response.data?.map { it.toPokemonEntity() } ?: emptyList()

            pokeDao.clearAll()
            pokeDao.insertAll(list)
            appDatastore.setAlreadyLoaded(true)
        }
    }

    override suspend fun updatePokemonBackgroundColor(color: String, id: Int) {
        pokeDao.updatePokemonColor(id, color)
    }

    override fun getPokemonDetail(pokemonItem: PokemonItem) =
        flow {
            try {
                emit(ResultState.Loading)
                val pokemonDetailResponse = pokeService.getPokemonAbilities(pokemonItem.id)
                val abilities = pokemonDetailResponse.abilities?.map { it.ability.name.orEmpty() }
                    ?: emptyList()
                val types =
                    pokemonDetailResponse.types?.map { it.type?.name.orEmpty() } ?: emptyList()
                val states = pokemonDetailResponse.stats?.map {
                    Stat(
                        name = it.stat?.name.orEmpty(),
                        baseStat = it.baseStat ?: 0,
                        effort = it.effort ?: 0
                    )
                } ?: emptyList()


                val pokemonSpeciesResponse = pokeService.getPokemonDescription(pokemonItem.id)
                val description = pokemonSpeciesResponse.getEnglishDescription()
                val idEvolutionChain = getPokemonIdFromUrl(pokemonSpeciesResponse.evolutionChains?.url.orEmpty())

                val evolutionChainResponse = pokeService.getEvolutionChain(idEvolutionChain.toInt())
                val evolutionChain = mapToEvolutionList(evolutionChainResponse)

                val pokemonDetail = PokemonDetail(
                    name = pokemonItem.name,
                    imageUrl = pokemonItem.image,
                    abilities = abilities,
                    description = description.orEmpty(),
                    types = types,
                    stats = states,
                    evolutionChain = evolutionChain,
                )
                emit(ResultState.Success(pokemonDetail))

            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e.message.orEmpty()))
            }
        }.flowOn(Dispatchers.IO)
}