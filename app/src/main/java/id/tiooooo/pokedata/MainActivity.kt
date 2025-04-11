package id.tiooooo.pokedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import id.tiooooo.pokedata.ui.pages.login.LoginRoute
import id.tiooooo.pokedata.ui.theme.PokeDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeDataTheme {
                Navigator(
                    screen = LoginRoute(),
                    disposeBehavior = NavigatorDisposeBehavior(),
                    onBackPressed = { true },
                ) { navigator ->
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}