package id.tiooooo.pokedata.ui.pages.splash

import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.PokemonRepository
import id.tiooooo.pokedata.data.api.repository.UserRepository
import kotlinx.coroutines.launch

class SplashScreenModel(
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository,
) : BaseScreenModel<SplashState, SplashIntent, SplashEffect>(
    initialState = SplashState()
) {
    override fun reducer(state: SplashState, intent: SplashIntent): SplashState {
        return when (intent) {
            is SplashIntent.CheckLogin -> state.copy(isLoading = true)
        }
    }

    override suspend fun handleIntentSideEffect(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.CheckLogin -> {
                screenModelScope.launch {
                    val isLoggedIn = userRepository.isLogin()
                    pokemonRepository.refreshFromNetwork(true)
                    if (isLoggedIn) {
                        sendEffect(SplashEffect.NavigateToHome)
                    } else {
                        sendEffect(SplashEffect.NavigateToLogin)
                    }
                }
            }
        }
    }
}

