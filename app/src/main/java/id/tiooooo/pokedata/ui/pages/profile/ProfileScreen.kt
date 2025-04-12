package id.tiooooo.pokedata.ui.pages.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import id.tiooooo.pokedata.ui.pages.profile.component.ProfileHeader
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium12
import id.tiooooo.pokedata.utils.AppConstants

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
                    .fillMaxSize(),
            ) {
                BasicTopBarTitle(
                    modifier = Modifier
                        .wrapContentSize(),
                    title = "Profile",
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
                        .padding(vertical = SMALL_PADDING, horizontal = MEDIUM_PADDING),
                    text = "Versi Aplikasi ${AppConstants.APP_VERSION}",
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
                Text(text = "Keluar")
            }
        }
    }
}