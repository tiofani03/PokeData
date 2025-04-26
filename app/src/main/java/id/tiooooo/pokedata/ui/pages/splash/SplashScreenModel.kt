package id.tiooooo.pokedata.ui.pages.splash

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.PokemonRepository
import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.utils.localization.LocalizationManager
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class SplashScreenModel(
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository,
    private val appDatastore: AppDatastore,
    private val localizationManager: LocalizationManager,
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
                val isLoggedIn = userRepository.isLogin()
                pokemonRepository.refreshFromNetwork(true)

                val remoteConfig = Firebase.remoteConfig
                val configSettings = remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 3600
                }
                remoteConfig.setConfigSettingsAsync(configSettings).await()

                try {
                    remoteConfig.fetchAndActivate().await()
                    val languagePack = remoteConfig.getString("language_pack")
                    appDatastore.setLanguagePackage(languagePack)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                appDatastore.selectedLanguage
                    .map { it.ifEmpty { "en" } }
                    .distinctUntilChanged()
                    .firstOrNull()?.let { lang ->
                        localizationManager.loadLanguage(lang)
                    }
                handleNavigate(isLoggedIn)
            }
        }
    }

    private suspend fun handleNavigate(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            sendEffect(SplashEffect.NavigateToHome)
        } else {
            sendEffect(SplashEffect.NavigateToLogin)
        }
    }
}

