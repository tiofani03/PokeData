package id.tiooooo.pokedata.ui.pages.splash

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository
import kotlinx.coroutines.delay

class SplashScreenModel(
    private val userRepository: UserRepository
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
                delay(3000)
                val isLoggedIn = userRepository.isLogin()
                if (isLoggedIn) {
                    sendEffect(SplashEffect.NavigateToHome)
                } else {
                    sendEffect(SplashEffect.NavigateToLogin)
                }
            }
        }
    }
}

