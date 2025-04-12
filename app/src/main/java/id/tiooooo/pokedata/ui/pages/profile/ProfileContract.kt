package id.tiooooo.pokedata.ui.pages.profile

sealed interface ProfileEffect {
    data object NavigateToLogin : ProfileEffect
}

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val name: String = "",
    val email: String = "",
)

sealed interface ProfileIntent {
    data object ExecuteLogout : ProfileIntent
    data object InitProfile: ProfileIntent
}