package id.tiooooo.pokedata.ui.pages.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.pages.login.LoginRoute
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING

class DashboardRoute : Screen {
    @Composable
    override fun Content() {
        DashboardScreen(
            modifier = Modifier,
            screenModel = koinScreenModel()
        )
    }
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    screenModel: DashboardScreenModel,
) {
    val navigator = LocalNavigator.currentOrThrow
    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is DashboardEffect.NavigateToLogin -> navigator.replaceAll(LoginRoute())
            }
        }
    }

    BaseScaffold {
        Column(
            modifier = modifier,
        ) {
            Text("Ini Halaman Dashboard")
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MEDIUM_PADDING),
                onClick = { screenModel.dispatch(DashboardIntent.ExecuteLogout) }
            ) {
                Text(text = "Logout")
            }
        }
    }
}