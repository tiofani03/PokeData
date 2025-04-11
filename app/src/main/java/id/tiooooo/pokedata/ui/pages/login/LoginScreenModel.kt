package id.tiooooo.pokedata.ui.pages.login

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository

class LoginScreenModel(
    private val userRepository: UserRepository,
) : BaseScreenModel<LoginState, LoginIntent, LoginEffect>(
    initialState = LoginState()
) {
    override fun reducer(state: LoginState, intent: LoginIntent): LoginState {
        return when (intent) {
            is LoginIntent.UpdateEmail -> {
                validateButton()
                state.copy(email = intent.value)
            }

            is LoginIntent.UpdatePassword -> {
                validateButton()
                state.copy(password = intent.value)
            }

            is LoginIntent.ExecuteLogin -> state.copy(isLoading = true, error = null)
            is LoginIntent.NavigateToRegister -> state
        }
    }

    private fun validateButton() {
        val currentState = state.value
        if (currentState.email.isEmpty() && currentState.password.isEmpty()) {
            setState { currentState.copy(isButtonEnable = false) }
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
                    }
                    else setState {
                        it.copy(
                            isLoading = false,
                            error = "Email atau password salah"
                        )
                    }
                }
            }

            is LoginIntent.NavigateToRegister -> {
                sendEffect(LoginEffect.NavigateToRegister)
            }

            else -> Unit
        }
    }
}
