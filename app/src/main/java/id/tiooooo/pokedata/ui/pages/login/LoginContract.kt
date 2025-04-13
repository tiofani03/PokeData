package id.tiooooo.pokedata.ui.pages.login

sealed interface LoginEffect {
    data object NavigateToHome : LoginEffect
    data object NavigateToRegister : LoginEffect
    data class ShowErrorMessage(val message: String): LoginEffect
}

data class LoginState(
    val isLoading: Boolean = false,
    val isButtonEnable: Boolean = false,
    val email: String = "",
    val password: String = "",
    val error: String? = null,
)

sealed interface LoginIntent {
    data object ExecuteLogin : LoginIntent
    data object NavigateToRegister: LoginIntent
    data class UpdateEmail(val value: String) : LoginIntent
    data class UpdatePassword(val value: String) : LoginIntent
}