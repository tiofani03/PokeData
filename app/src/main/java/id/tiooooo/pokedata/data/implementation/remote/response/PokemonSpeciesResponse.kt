package id.tiooooo.pokedata.data.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntryResponse>?
)

data class FlavorTextEntryResponse(
    @SerializedName("flavor_text") val flavorText: String?,
    @SerializedName("language") val language: NamedApiResourceResponse?
)

data class NamedApiResourceResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
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

