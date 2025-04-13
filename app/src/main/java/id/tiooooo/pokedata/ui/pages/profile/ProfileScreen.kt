package id.tiooooo.pokedata.ui.pages.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.component.BasicTopBarTitle
import id.tiooooo.pokedata.ui.pages.login.LoginRoute
import id.tiooooo.pokedata.ui.pages.profile.component.ChooseLanguageDialog
import id.tiooooo.pokedata.ui.pages.profile.component.ProfileHeader
import id.tiooooo.pokedata.ui.pages.profile.component.SettingItem
import id.tiooooo.pokedata.ui.pages.profile.component.SettingItemLayout
import id.tiooooo.pokedata.ui.pages.profile.component.ChooseThemeDialog
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium12
import id.tiooooo.pokedata.ui.theme.textMedium16
import id.tiooooo.pokedata.utils.AppConstants
import id.tiooooo.pokedata.utils.AppConstants.APP_VERSION
import id.tiooooo.pokedata.utils.AppLanguage
import id.tiooooo.pokedata.utils.AppTheme
import id.tiooooo.pokedata.utils.localization.stringRes

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    screenModel: ProfileScreenModel,
) {
    val navigator = LocalNavigator.currentOrThrow
    val state by screenModel.state.collectAsState()
    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is ProfileEffect.NavigateToLogin -> navigator.replaceAll(LoginRoute())
            }
        }
    }

    BaseScaffold {
        Box(
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                BasicTopBarTitle(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = MEDIUM_PADDING),
                    title = stringRes("profile"),
                )

                ProfileHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MEDIUM_PADDING),
                    name = state.name,
                    email = state.email,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MEDIUM_PADDING)
                        .padding(vertical = SMALL_PADDING, horizontal = MEDIUM_PADDING),
                    text = stringRes("settings"),
                    style = textMedium16().copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                SettingItemLayout(
                    item = SettingItem(
                        title = stringRes("theme_settings"),
                        icon = Icons.Filled.DarkMode,
                    ),
                    onSettingClicked = { screenModel.dispatch(ProfileIntent.ShowDialogTheme(true)) }
                )

                SettingItemLayout(
                    item = SettingItem(
                        title = stringRes("language_settings"),
                        icon = Icons.Filled.Translate,
                    ),
                    onSettingClicked = { screenModel.dispatch(ProfileIntent.ShowDialogLanguage(true)) }
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MEDIUM_PADDING),
                    text = stringRes("version_app", APP_VERSION),
                    textAlign = TextAlign.Center,
                    style = textMedium12().copy(
                        fontWeight = FontWeight.Normal,
                    )
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING, vertical = SMALL_PADDING)
                    .align(Alignment.BottomCenter),
                onClick = { screenModel.dispatch(ProfileIntent.ExecuteLogout) },
            ) {
                Text(text = stringRes("logout"))
            }
        }
    }

    if (state.isShowDialogTheme) {
        ChooseThemeDialog(
            currentTheme = AppTheme.fromValue(state.activeTheme),
            onDismiss = { screenModel.dispatch(ProfileIntent.ShowDialogTheme(false)) },
            onConfirm = {
                screenModel.dispatch(ProfileIntent.ShowDialogTheme(false))
                screenModel.dispatch(ProfileIntent.UpdateTheme(it.label))
            }
        )
    }

    if (state.isShowDialogLanguage) {
        ChooseLanguageDialog(
            currentLanguage = AppLanguage.fromValue(state.selectedLanguage),
            onDismiss = { screenModel.dispatch(ProfileIntent.ShowDialogLanguage(false)) },
            onConfirm = {
                screenModel.dispatch(ProfileIntent.ShowDialogLanguage(false))
                screenModel.dispatch(ProfileIntent.UpdateLanguage(it.code))
            }
        )
    }
}