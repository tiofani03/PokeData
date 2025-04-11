package id.tiooooo.pokedata.ui.pages.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class LoginRoute : Screen {
    @Composable
    override fun Content() {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            screenModel = koinScreenModel()
        )
    }
}