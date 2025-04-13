package id.tiooooo.pokedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.ui.pages.splash.SplashRoute
import id.tiooooo.pokedata.ui.theme.PokeDataTheme
import id.tiooooo.pokedata.utils.SetupStatusBarAppearance
import id.tiooooo.pokedata.utils.localization.ProvideLocalization
import id.tiooooo.pokedata.utils.localization.rememberLocalization
import id.tiooooo.pokedata.utils.rememberAppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val appDatastore: AppDatastore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme = rememberAppTheme(appDatastore)
            val localizationProvider = rememberLocalization(appDatastore)
            SetupStatusBarAppearance(darkTheme)

            ProvideLocalization(localizationProvider) {
                PokeDataTheme(darkTheme) {
                    Navigator(
                        screen = SplashRoute(),
                        disposeBehavior = NavigatorDisposeBehavior(),
                        onBackPressed = { true },
                    ) { navigator ->
                        SlideTransition(navigator = navigator)
                    }
                }
            }
        }
    }
}