package id.tiooooo.pokedata.data.implementation.remote.response

import com.google.gson.annotations.SerializedName
import id.tiooooo.pokedata.data.api.model.EvolutionChain
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_BASE_URL

data class EvolutionChainContainerResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("baby_trigger_item") val babyTriggerItem: Any?,
    @SerializedName("chain") val chain: EvolutionChainResponse
)

data class EvolutionChainResponse(
    @SerializedName("species") val species: NamedApiResourceResponse,
    @SerializedName("evolution_details") val evolutionDetails: List<EvolutionDetail>?,
    @SerializedName("evolves_to") val evolvesTo: List<EvolutionChainResponse>
)

data class EvolutionDetail(
    @SerializedName("min_level") val minLevel: Int?,
    @SerializedName("trigger") val trigger: NamedApiResourceResponse
)

fun mapToEvolutionList(response: EvolutionChainContainerResponse): List<EvolutionChain> {
    val evolutionList = mutableListOf<EvolutionChain>()

    fun extractEvolutions(chain: EvolutionChainResponse) {
        val speciesName = chain.species.name
        val speciesImage =
            "$IMAGE_BASE_URL${getPokemonIdFromUrl(chain.species.url.orEmpty())}.png"
        evolutionList.add(
            EvolutionChain(
                imageUrl = speciesImage,
                name = speciesName.orEmpty()
            )
        )

        chain.evolvesTo.forEach { extractEvolutions(it) }
    }

    extractEvolutions(response.chain)
    return evolutionList
}

fun getPokemonIdFromUrl(url: String): String {
    return url.split("/").dropLast(1).last()
}
