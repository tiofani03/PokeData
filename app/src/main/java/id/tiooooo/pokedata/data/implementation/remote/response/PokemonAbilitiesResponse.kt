package id.tiooooo.pokedata.data.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonDetailAbilitiesResponse(
    @SerializedName("abilities") val abilities: List<AbilitySlotResponse>?
)

data class AbilitySlotResponse(
    @SerializedName("ability")
    val ability: NamedApiResource,
)

data class NamedApiResource(
    @SerializedName("name")
    val name: String?,
)

