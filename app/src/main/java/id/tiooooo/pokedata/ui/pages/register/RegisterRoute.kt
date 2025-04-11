package id.tiooooo.pokedata.ui.pages.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class RegisterRoute : Screen {
    @Composable
    override fun Content() {
        RegisterScreen(
            modifier = Modifier.fillMaxSize(),
            screenModel = koinScreenModel()
        )
    }
}