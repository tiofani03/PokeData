package id.tiooooo.pokedata.ui.pages.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class DashboardRoute : Screen {

    @Composable
    override fun Content() {
        DashboardScreen(
            modifier = Modifier.fillMaxSize(),
            screenModel = koinScreenModel(),
            profileScreenModel = koinScreenModel(),
        )
    }
}