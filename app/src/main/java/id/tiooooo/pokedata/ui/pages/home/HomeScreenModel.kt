package id.tiooooo.pokedata.ui.pages.home

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val pokemonRepository: PokemonRepository
) : BaseScreenModel<HomeState, HomeIntent, HomeEffect>(
    initialState = HomeState()
) {
    init {
        screenModelScope.launch {
            pokemonRepository.refreshFromNetwork(true)
        }
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pokemonFlow = state
        .map { it.searchQuery }
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            pokemonRepository.getPagedPokemon(query)
        }
        .cachedIn(screenModelScope)


    override fun reducer(state: HomeState, intent: HomeIntent): HomeState {
        return when (intent) {
            is HomeIntent.UpdateSearchQuery -> state.copy(searchQuery = intent.query)
            else -> state
        }
    }

    override suspend fun handleIntentSideEffect(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.UpdateColor -> {
                pokemonRepository.updatePokemonBackgroundColor(
                    color = intent.color,
                    id = intent.id
                )
            }

            is HomeIntent.NavigateToDetail -> {
                sendEffect(HomeEffect.NavigateToDetail(intent.pokemon))
            }

            else -> Unit
        }
    }
}