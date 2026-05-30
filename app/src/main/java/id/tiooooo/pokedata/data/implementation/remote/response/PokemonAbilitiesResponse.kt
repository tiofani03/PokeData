package id.tiooooo.pokedata.data.implementation.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailAbilitiesResponse(
    @SerialName("abilities") val abilities: List<AbilitySlotResponse>? = null,
    @SerialName("types") val types: List<TypeResponse>? = null,
    @SerialName("stats") val stats: List<StatsResponse>? = null,
)

@Serializable
data class AbilitySlotResponse(
    @SerialName("ability")
    val ability: NamedApiResource,
)

@Serializable
data class NamedApiResource(
    @SerialName("name")
    val name: String? = null,
)

@Serializable
data class TypeResponse(
    @SerialName("slot") val slot: Int? = null,
    @SerialName("type") val type: NamedApiResourceResponse? = null,
)

@Serializable
data class StatsResponse(
    @SerialName("base_stat") val baseStat: Int? = null,
    @SerialName("effort") val effort: Int? = null,
    @SerialName("stat") val stat: NamedApiResourceResponse? = null,
)
