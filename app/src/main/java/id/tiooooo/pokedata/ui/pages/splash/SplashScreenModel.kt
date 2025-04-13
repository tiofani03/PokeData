package id.tiooooo.pokedata.ui.pages.splash

import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.PokemonRepository
import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.utils.localization.LocalizationManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SplashScreenModel(
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository,
    private val appDatastore: AppDatastore,
    private val localizationManager: LocalizationManager,
) : BaseScreenModel<SplashState, SplashIntent, SplashEffect>(
    initialState = SplashState()
) {
    init {
        screenModelScope.launch {
            appDatastore.selectedLanguage
                .map { it.ifEmpty { "en" } }
                .distinctUntilChanged()
                .collectLatest { lang ->
                    localizationManager.loadLanguage(lang)
                }
        }
    }
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

