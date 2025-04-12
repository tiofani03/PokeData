package id.tiooooo.pokedata.data.api.model

data class PokemonDetail(
    val name: String,
    val imageUrl: String,
    val abilities: List<String>,
    val description: String
)

fun createPokemonDetailDefaultValue(): PokemonDetail = PokemonDetail(
    name = "",
    imageUrl = "",
    abilities = listOf(),
    description = ""
)
