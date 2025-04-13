package id.tiooooo.pokedata.ui.pages.profile

import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.utils.localization.LocalizationManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProfileScreenModel(
    private val userRepository: UserRepository,
    private val appDatastore: AppDatastore,
    private val localizationManager: LocalizationManager,
) : BaseScreenModel<ProfileState, ProfileIntent, ProfileEffect>(
    initialState = ProfileState()
) {
    init {
        dispatch(ProfileIntent.InitProfile)
        screenModelScope.launch {
            appDatastore.selectedLanguage
                .map { it.ifEmpty { "en" } }
                .distinctUntilChanged()
                .collectLatest { lang ->
                    localizationManager.loadLanguage(lang)
                }
        }
    }

    override fun reducer(state: ProfileState, intent: ProfileIntent): ProfileState {
        return when (intent) {
            is ProfileIntent.ExecuteLogout -> state
            is ProfileIntent.InitProfile -> state
            is ProfileIntent.UpdateTheme -> state
            is ProfileIntent.UpdateLanguage -> state
            is ProfileIntent.ShowDialogTheme -> state.copy(isShowDialogTheme = intent.value)
            is ProfileIntent.ShowDialogLanguage -> state.copy(isShowDialogLanguage = intent.value)
        }
    }

    override suspend fun handleIntentSideEffect(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.ExecuteLogout -> {
                userRepository.executeLogout().collect {
                    if (it) sendEffect(ProfileEffect.NavigateToLogin)
                }
            }

            is ProfileIntent.InitProfile -> {
                val currentState = state.value
                val theme = appDatastore.activeTheme.first()
                val selectedLanguage = appDatastore.selectedLanguage.first()
                userRepository.executeGetProfile().collect { user ->
                    if (user.uuid.isNotEmpty()) {
                        setState {
                            currentState.copy(
                                name = user.username,
                                email = user.email,
                                activeTheme = theme,
                                selectedLanguage = selectedLanguage,
                            )
                        }
                    } else {
                        dispatch(ProfileIntent.ExecuteLogout)
                    }
                }
            }

            is ProfileIntent.UpdateTheme -> {
                appDatastore.setActiveTheme(intent.value)
                val currentState = state.value
                setState {
                    currentState.copy(
                        activeTheme = intent.value
                    )
                }
            }

            is ProfileIntent.UpdateLanguage -> {
                appDatastore.setSelectedLanguage(intent.value)
                localizationManager.loadLanguage(intent.value)
                val currentState = state.value
                setState {
                    currentState.copy(
                        selectedLanguage = intent.value
                    )
                }
            }

            else -> Unit
        }
    }
}