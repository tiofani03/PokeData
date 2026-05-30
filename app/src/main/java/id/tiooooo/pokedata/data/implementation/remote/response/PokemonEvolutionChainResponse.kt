package id.tiooooo.pokedata.data.implementation.remote.response

import id.tiooooo.pokedata.data.api.model.EvolutionChain
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_BASE_URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainContainerResponse(
    @SerialName("id") val id: Int,
    @SerialName("baby_trigger_item") val babyTriggerItem: String? = null,
    @SerialName("chain") val chain: EvolutionChainResponse
)

@Serializable
data class EvolutionChainResponse(
    @SerialName("species") val species: NamedApiResourceResponse,
    @SerialName("evolution_details") val evolutionDetails: List<EvolutionDetail>? = null,
    @SerialName("evolves_to") val evolvesTo: List<EvolutionChainResponse>
)

@Serializable
data class EvolutionDetail(
    @SerialName("min_level") val minLevel: Int? = null,
    @SerialName("trigger") val trigger: NamedApiResourceResponse
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
