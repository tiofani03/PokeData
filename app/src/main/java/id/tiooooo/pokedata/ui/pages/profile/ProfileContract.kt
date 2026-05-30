package id.tiooooo.pokedata.ui.pages.profile

import com.localflow.sdk.data.model.LanguageInfo

sealed interface ProfileEffect {
    data object NavigateToLogin : ProfileEffect
}

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val name: String = "",
    val email: String = "",
    val activeTheme: String = "",
    val selectedLanguage: String = "",
    val isShowDialogTheme: Boolean = false,
    val isShowDialogLanguage: Boolean = false,
    val selectedLanguageObject: LanguageInfo = LanguageInfo("", "")
)

sealed interface ProfileIntent {
    data object ExecuteLogout : ProfileIntent
    data object InitProfile : ProfileIntent
    data class UpdateTheme(val value: String) : ProfileIntent
    data class UpdateLanguage(val value: LanguageInfo) : ProfileIntent
    data class ShowDialogTheme(val value: Boolean) : ProfileIntent
    data class ShowDialogLanguage(val value: Boolean) : ProfileIntent
    data object ForceSync : ProfileIntent
}