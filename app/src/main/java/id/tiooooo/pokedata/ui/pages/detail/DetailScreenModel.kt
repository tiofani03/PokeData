package id.tiooooo.pokedata.ui.pages.detail

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.PokemonRepository

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
                pokemonRepository.getPokemonDetail(pokemonItem = intent.pokemon).collect { detail ->
                    setState {
                        getCurrentState.copy(
                            pokemonDetail = detail
                        )
                    }
                }
            }
        }
    }
}