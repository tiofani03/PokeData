package id.tiooooo.pokedata.ui.pages.detail

import id.tiooooo.pokedata.data.api.model.PokemonDetail
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.api.model.createPokemonDetailDefaultValue

data class DetailState(
    val pokemonDetail: PokemonDetail = createPokemonDetailDefaultValue(),
    val isLoading: Boolean = true,
)

sealed interface DetailIntent {
    data class GetPokemonDetail(val pokemon: PokemonItem) : DetailIntent
}