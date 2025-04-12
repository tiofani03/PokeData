package id.tiooooo.pokedata.ui.pages.profile

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository

class ProfileScreenModel(
    private val userRepository: UserRepository,
) : BaseScreenModel<ProfileState, ProfileIntent, ProfileEffect>(
    initialState = ProfileState()
) {
    init {
        dispatch(ProfileIntent.InitProfile)
    }

    override fun reducer(state: ProfileState, intent: ProfileIntent): ProfileState {
        return when (intent) {
            is ProfileIntent.ExecuteLogout -> state
            is ProfileIntent.InitProfile -> state
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
                userRepository.executeGetProfile().collect { user ->
                    if (user.uuid.isNotEmpty()) {
                        setState {
                            currentState.copy(
                                name = user.username,
                                email = user.email
                            )
                        }
                    } else {
                        dispatch(ProfileIntent.ExecuteLogout)
                    }
                }
            }

        }
    }
}