package id.tiooooo.pokedata.data.implementation.remote.service

import id.tiooooo.pokedata.data.implementation.remote.response.ListResponse
import id.tiooooo.pokedata.data.implementation.remote.response.PokemonItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeService {
    @GET("api/v2/pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ListResponse<List<PokemonItemResponse>>
}
