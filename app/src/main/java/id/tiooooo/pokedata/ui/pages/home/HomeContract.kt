package id.tiooooo.pokedata.ui.pages.home

import androidx.compose.foundation.lazy.grid.LazyGridState
import id.tiooooo.pokedata.data.api.model.PokemonItem

data class HomeState(
    val searchQuery: String = "",
    val listState: LazyGridState = LazyGridState()
)

sealed interface HomeIntent {
    data class UpdateSearchQuery(val query: String) : HomeIntent
    data class UpdateColor(val id: Int, val color: String) : HomeIntent
    data class NavigateToDetail(val pokemon: PokemonItem) : HomeIntent
}

sealed interface HomeEffect {
    data class NavigateToDetail(val pokemon: PokemonItem) : HomeEffect
}
