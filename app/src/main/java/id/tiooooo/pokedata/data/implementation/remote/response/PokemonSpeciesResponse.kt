package id.tiooooo.pokedata.data.implementation.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesResponse(
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntryResponse>? = null,
    @SerialName("evolution_chain") val evolutionChains: NamedApiResourceResponse? = null,
)

@Serializable
data class FlavorTextEntryResponse(
    @SerialName("flavor_text") val flavorText: String? = null,
    @SerialName("language") val language: NamedApiResourceResponse? = null,
)

@Serializable
data class NamedApiResourceResponse(
    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null
)

fun PokemonSpeciesResponse?.getEnglishDescription(): String? {
    return this?.flavorTextEntries
        ?.firstOrNull { it.language?.name == "en" }
        ?.flavorText
        ?.let { cleanFlavorText(it) }
}

fun cleanFlavorText(raw: String): String {
    return raw
        .replace("\n", " ")
        .replace("\u000c", " ")
        .replace(Regex("\\s+"), " ")
        .trim()
}
