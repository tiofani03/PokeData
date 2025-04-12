package id.tiooooo.pokedata.ui.pages.detail

import androidx.compose.foundation.lazy.grid.LazyGridState
import id.tiooooo.pokedata.data.api.model.PokemonDetail
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.api.model.createPokemonDetailDefaultValue

data class DetailState(
    val pokemonDetail: PokemonDetail = createPokemonDetailDefaultValue()
)

sealed interface DetailIntent {
    data class GetPokemonDetail(val pokemon: PokemonItem) : DetailIntent
}