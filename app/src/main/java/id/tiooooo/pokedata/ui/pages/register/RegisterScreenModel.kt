package id.tiooooo.pokedata.ui.pages.register

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.utils.localization.LocalizationManager

class RegisterScreenModel(
    private val userRepository: UserRepository,
    private val localizationManager: LocalizationManager,
) : BaseScreenModel<RegisterState, RegisterIntent, RegisterEffect>(RegisterState()) {

    override fun reducer(state: RegisterState, intent: RegisterIntent): RegisterState {
        return when (intent) {
            is RegisterIntent.UpdateEmail -> {
                val error =
                    if (intent.value.isBlank()) localizationManager.getString("register_email_must_not_be_empty") else ""
                state.copy(email = intent.value, emailError = error)
            }

            is RegisterIntent.UpdateName -> {
                val error =
                    if (intent.value.isBlank()) localizationManager.getString("register_name_must_not_be_empty") else ""
                state.copy(name = intent.value, nameError = error)
            }

            is RegisterIntent.UpdatePassword -> state
            is RegisterIntent.UpdateConfirmPassword -> state
            is RegisterIntent.ExecuteRegister -> state.copy(isLoading = true)
        }
    }

    override suspend fun handleIntentSideEffect(intent: RegisterIntent) {
        val currentState = state.value

        fun String.validateNotEmpty(fieldName: String): String? =
            if (isBlank()) localizationManager.getString(
                "register_general_must_not_be_empty",
                fieldName,
            ) else null

        fun validatePasswordMatch(): String? =
            if (currentState.password != currentState.confirmPassword) localizationManager.getString(
                "register_password_must_be_same"
            ) else null

        when (intent) {
            is RegisterIntent.UpdatePassword -> {
                val error =
                    intent.value.validateNotEmpty(localizationManager.getString("register_password"))
                setState {
                    it.copy(password = intent.value, passwordError = error.orEmpty())
                }
                validatePasswordFields()
            }

            is RegisterIntent.UpdateConfirmPassword -> {
                val error =
                    intent.value.validateNotEmpty(localizationManager.getString("register_confirmation_password"))
                setState {
                    it.copy(confirmPassword = intent.value, confirmPasswordError = error.orEmpty())
                }
                validatePasswordFields()
            }

            is RegisterIntent.ExecuteRegister -> {
                val emailError =
                    currentState.email.validateNotEmpty(localizationManager.getString("email"))
                val nameError =
                    currentState.name.validateNotEmpty(localizationManager.getString("register_name"))
                val passwordError =
                    currentState.password.validateNotEmpty(localizationManager.getString("register_password"))
                val confirmPasswordError =
                    currentState.confirmPassword.validateNotEmpty(localizationManager.getString("register_confirmation_password"))
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
                        sendEffect(RegisterEffect.ShowError(localizationManager.getString("register_failed_message")))
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
            if (current.password != current.confirmPassword) localizationManager.getString("register_password_must_be_same") else null

        setState {
            it.copy(
                confirmPasswordError = mismatchError.orEmpty()
            )
        }
    }
}



