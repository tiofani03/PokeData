package id.tiooooo.pokedata.data.implementation.remote.service

import id.tiooooo.pokedata.data.implementation.remote.response.EvolutionChainContainerResponse
import id.tiooooo.pokedata.data.implementation.remote.response.ListResponse
import id.tiooooo.pokedata.data.implementation.remote.response.PokemonDetailAbilitiesResponse
import id.tiooooo.pokedata.data.implementation.remote.response.PokemonItemResponse
import id.tiooooo.pokedata.data.implementation.remote.response.PokemonSpeciesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface PokeService {
    suspend fun getPokemon(
        limit: Int,
        offset: Int
    ): ListResponse<List<PokemonItemResponse>>

    suspend fun getPokemonAbilities(
        id: Int
    ): PokemonDetailAbilitiesResponse

    suspend fun getPokemonDescription(
        id: Int
    ): PokemonSpeciesResponse

    suspend fun getEvolutionChain(
        id: Int
    ): EvolutionChainContainerResponse
}

class PokeServiceImpl(
    private val client: HttpClient
) : PokeService {

    override suspend fun getPokemon(limit: Int, offset: Int): ListResponse<List<PokemonItemResponse>> {
        return client.get("api/v2/pokemon") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
    }

    override suspend fun getPokemonAbilities(id: Int): PokemonDetailAbilitiesResponse {
        return client.get("api/v2/pokemon/$id").body()
    }

    override suspend fun getPokemonDescription(id: Int): PokemonSpeciesResponse {
        return client.get("api/v2/pokemon-species/$id").body()
    }

    override suspend fun getEvolutionChain(id: Int): EvolutionChainContainerResponse {
        return client.get("api/v2/evolution-chain/$id").body()
    }
}
