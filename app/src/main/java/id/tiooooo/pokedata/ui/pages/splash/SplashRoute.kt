package id.tiooooo.pokedata.ui.pages.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class SplashRoute : Screen {
    @Composable
    override fun Content() {
        val splashScreenModel = koinScreenModel<SplashScreenModel>()
        SplashScreen(
            modifier = Modifier.fillMaxSize(),
            screenModel = splashScreenModel,
        )
    }
}