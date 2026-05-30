package id.tiooooo.pokedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import com.localflow.sdk.ui.compose.LocalflowProvider
import com.localflow.sdk.ui.compose.LocalflowSyncEffect
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.ui.pages.splash.SplashRoute
import id.tiooooo.pokedata.ui.theme.PokeDataTheme
import id.tiooooo.pokedata.utils.SetupStatusBarAppearance
import id.tiooooo.pokedata.utils.rememberAppTheme
import id.tiooooo.pokedata.utils.rememberSelectedLanguage
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val appDatastore: AppDatastore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val darkTheme = rememberAppTheme(appDatastore)
            AppLocalflowProvider(appDatastore) {
                LocalflowSyncEffect()
                SetupStatusBarAppearance(darkTheme)
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


@Composable
fun AppLocalflowProvider(
    appDatastore: AppDatastore,
    content: @Composable () -> Unit
) {
    val language = rememberSelectedLanguage(appDatastore)

    key(language) {
        LocalflowProvider(languageCode = language) {
            content()
        }
    }
}