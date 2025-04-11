package id.tiooooo.pokedata.ui.pages.register

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository

class RegisterScreenModel(
    private val userRepository: UserRepository,
) : BaseScreenModel<RegisterState, RegisterIntent, RegisterEffect>(RegisterState()) {

    override fun reducer(state: RegisterState, intent: RegisterIntent): RegisterState {
        return when (intent) {
            is RegisterIntent.UpdateEmail -> {
                val error = if (intent.value.isBlank()) "Email tidak boleh kosong" else ""
                state.copy(email = intent.value, emailError = error)
            }

            is RegisterIntent.UpdateName -> {
                val error = if (intent.value.isBlank()) "Nama tidak boleh kosong" else ""
                state.copy(name = intent.value, nameError = error)
            }

            is RegisterIntent.UpdatePassword -> state
            is RegisterIntent.UpdateRePassword -> state
            is RegisterIntent.ExecuteRegister -> state.copy(isLoading = true)
        }
    }

    override suspend fun handleIntentSideEffect(intent: RegisterIntent) {
        val currentState = state.value

        fun String.validateNotEmpty(fieldName: String): String? =
            if (isBlank()) "$fieldName tidak boleh kosong" else null

        fun validatePasswordMatch(): String? =
            if (currentState.password != currentState.confirmPassword) "Password tidak sama" else null

        when (intent) {
            is RegisterIntent.UpdatePassword -> {
                val error = intent.value.validateNotEmpty("Password")
                setState {
                    it.copy(password = intent.value, passwordError = error.orEmpty())
                }
                validatePasswordFields()
            }

            is RegisterIntent.UpdateRePassword -> {
                val error = intent.value.validateNotEmpty("Password")
                setState {
                    it.copy(confirmPassword = intent.value, confirmPasswordError = error.orEmpty())
                }
                validatePasswordFields()
            }

            is RegisterIntent.ExecuteRegister -> {
                val emailError = currentState.email.validateNotEmpty("Email")
                val nameError = currentState.name.validateNotEmpty("Nama")
                val passwordError = currentState.password.validateNotEmpty("Password")
                val confirmPasswordError = currentState.confirmPassword.validateNotEmpty("Password")
                val mismatchError = validatePasswordMatch()

                if (emailError != null || nameError != null || passwordError != null || confirmPasswordError != null || mismatchError != null) {
                    setState {
                        it.copy(
                            isLoading = false,
                            emailError = emailError.orEmpty(),
                            nameError = nameError.orEmpty(),
                            passwordError = passwordError ?: mismatchError.orEmpty(),
                            confirmPasswordError = confirmPasswordError ?: mismatchError.orEmpty(),
                        )
                    }
                    return
                }

                setState { it.copy(isLoading = true) }

                userRepository.executeRegister(
                    currentState.email.trim(),
                    currentState.password.trim(),
                    currentState.name.trim()
                ).collect { result ->
                    if (result) {
                        sendEffect(RegisterEffect.NavigateToLogin)
                    } else {
                        sendEffect(RegisterEffect.ShowError("Registrasi gagal"))
                        setState { it.copy(isLoading = false) }
                    }
                }
            }

            else -> Unit
        }
    }

    private fun validatePasswordFields() {
        val current = state.value
        val mismatchError =
            if (current.password != current.confirmPassword) "Password tidak sama" else null

        setState {
            it.copy(
                confirmPasswordError = mismatchError.orEmpty()
            )
        }
    }
}



