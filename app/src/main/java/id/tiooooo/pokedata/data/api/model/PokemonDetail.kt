package id.tiooooo.pokedata.data.api.model

data class PokemonDetail(
    val name: String,
    val imageUrl: String,
    val abilities: List<String>,
    val description: String,
    val types: List<String>,
    val stats: List<Stat>,
    val evolutionChain: List<EvolutionChain>,
)

fun createPokemonDetailDefaultValue(): PokemonDetail = PokemonDetail(
    name = "",
    imageUrl = "",
    abilities = listOf(),
    description = "",
    types = listOf(),
    stats = listOf(),
    evolutionChain = listOf(),
)

data class Stat(
    val name: String,
    val baseStat: Int,
    val effort: Int,
)

data class EvolutionChain(
    val name: String,
    val imageUrl: String,
)
