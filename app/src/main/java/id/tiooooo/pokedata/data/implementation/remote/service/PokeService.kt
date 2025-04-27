package id.tiooooo.pokedata.data.implementation.remote.service

import id.tiooooo.pokedata.data.implementation.remote.response.EvolutionChainContainerResponse
import id.tiooooo.pokedata.data.implementation.remote.response.ListResponse
import id.tiooooo.pokedata.data.implementation.remote.response.PokemonDetailAbilitiesResponse
import id.tiooooo.pokedata.data.implementation.remote.response.PokemonItemResponse
import id.tiooooo.pokedata.data.implementation.remote.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {
    @GET("api/v2/pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ListResponse<List<PokemonItemResponse>>

    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonAbilities(
        @Path("id") id: Int
    ): PokemonDetailAbilitiesResponse

    @GET("api/v2/pokemon-species/{id}")
    suspend fun getPokemonDescription(
        @Path("id") id: Int
    ): PokemonSpeciesResponse

    @GET("api/v2/evolution-chain/{id}")
    suspend fun getEvolutionChain(
        @Path("id") id: Int
    ): EvolutionChainContainerResponse

}
