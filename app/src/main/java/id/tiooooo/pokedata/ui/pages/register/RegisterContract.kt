package id.tiooooo.pokedata.ui.pages.register

sealed interface RegisterEffect {
    data object NavigateToLogin : RegisterEffect
    data class ShowError(val message: String) : RegisterEffect
}

data class RegisterState(
    val email: String = "",
    val emailError: String = "",
    val name: String = "",
    val nameError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String = "",
    val isLoading: Boolean = false,
    val isButtonEnable: Boolean = false,
    val errorMessage: String? = null
)

sealed interface RegisterIntent {
    data object ExecuteRegister : RegisterIntent
    data class UpdateEmail(val value: String) : RegisterIntent
    data class UpdateName(val value: String) : RegisterIntent
    data class UpdatePassword(val value: String) : RegisterIntent
    data class UpdateRePassword(val value: String) : RegisterIntent
}