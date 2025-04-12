package id.tiooooo.pokedata.ui.pages.home

import androidx.compose.foundation.lazy.grid.LazyGridState

data class HomeState(
    val searchQuery: String = "",
    val listState: LazyGridState = LazyGridState()
)

sealed interface HomeIntent {
    data class UpdateSearchQuery(val query: String) : HomeIntent
    data class UpdateColor(val id: Int, val color: String) : HomeIntent
    data class NavigateToDetail(val id: Int) : HomeIntent
}

sealed interface HomeEffect {
    data class NavigateToDetail(val id: Int) : HomeEffect
}
