package id.tiooooo.pokedata.ui.pages.detail

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.PokemonRepository
import id.tiooooo.pokedata.utils.wrapper.ResultState

class DetailScreenModel(
    private val pokemonRepository: PokemonRepository,
) : BaseScreenModel<DetailState, DetailIntent, Nothing>(
    initialState = DetailState()
) {
    override fun reducer(state: DetailState, intent: DetailIntent): DetailState {
        return when (intent) {
            is DetailIntent.GetPokemonDetail -> state
        }
    }

    override suspend fun handleIntentSideEffect(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.GetPokemonDetail -> {
                val currentState = state.value
                pokemonRepository.getPokemonDetail(pokemonItem = intent.pokemon).collect { state ->
                    when (state) {
                        is ResultState.Success -> {
                            setState {
                                currentState.copy(
                                    pokemonDetail = state.data,
                                    isLoading = false,
                                )
                            }
                        }

                        else -> Unit
                    }

                }
            }
        }
    }
}