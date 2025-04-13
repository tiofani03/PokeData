package id.tiooooo.pokedata.ui.pages.login

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.utils.localization.LocalizationManager

class LoginScreenModel(
    private val userRepository: UserRepository,
    private val localizationManager: LocalizationManager,
) : BaseScreenModel<LoginState, LoginIntent, LoginEffect>(
    initialState = LoginState()
) {
    override fun reducer(state: LoginState, intent: LoginIntent): LoginState {
        return when (intent) {
            is LoginIntent.UpdateEmail -> {
                state.copy(email = intent.value)
            }

            is LoginIntent.UpdatePassword -> {
                state.copy(password = intent.value)
            }

            is LoginIntent.ExecuteLogin -> state.copy(isLoading = true, error = null)
            is LoginIntent.NavigateToRegister -> state
        }
    }

    private fun validateButton() {
        val currentState = state.value
        if (currentState.email.isEmpty() || currentState.password.isEmpty()) {
            setState { currentState.copy(isButtonEnable = false) }
            return
        }
        setState { currentState.copy(isButtonEnable = true) }
    }

    override suspend fun handleIntentSideEffect(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ExecuteLogin -> {
                val email = state.value.email.trim()
                val password = state.value.password.trim()

                userRepository.executeLogin(email, password).collect { isUserFound ->
                    if (isUserFound) {
                        sendEffect(LoginEffect.NavigateToHome)
                    } else {
                        sendEffect(
                            LoginEffect.ShowErrorMessage(
                                localizationManager.getString("wrong_email_or_password")
                            )
                        )
                    }
                }

                setState {
                    state.value.copy(isLoading = false)
                }
            }

            is LoginIntent.NavigateToRegister -> {
                sendEffect(LoginEffect.NavigateToRegister)
            }

            is LoginIntent.UpdateEmail, is LoginIntent.UpdatePassword -> {
                validateButton()
            }
        }
    }
}
