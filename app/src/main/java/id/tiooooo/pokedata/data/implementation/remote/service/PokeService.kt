package id.tiooooo.pokedata.data.implementation.remote.service

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

interface PokeService {
    @GET("api/v2/pokemon")
    suspend fun getPokemon(
    ): ApiResponse<List<PokemonResponse>>
}

data class ApiResponse<T>(
    @SerializedName("results") var data: T? = null,
)

data class PokemonResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?,
)