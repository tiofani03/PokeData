package id.tiooooo.pokedata.data.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonDetailAbilitiesResponse(
    @SerializedName("abilities") val abilities: List<AbilitySlotResponse>?,
    @SerializedName("types") val types: List<TypeResponse>?,
    @SerializedName("stats") val stats: List<StatsResponse>?,

)

data class AbilitySlotResponse(
    @SerializedName("ability")
    val ability: NamedApiResource,
)

data class NamedApiResource(
    @SerializedName("name")
    val name: String?,
)

data class TypeResponse(
    @SerializedName("slot") val slot: Int?,
    @SerializedName("type") val type: NamedApiResourceResponse?
)

data class StatsResponse(
    @SerializedName("base_stat") val baseStat: Int?,
    @SerializedName("effort") val effort: Int?,
    @SerializedName("stat") val stat: NamedApiResourceResponse?
)

