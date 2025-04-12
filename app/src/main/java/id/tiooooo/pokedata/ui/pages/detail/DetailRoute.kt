package id.tiooooo.pokedata.ui.pages.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class DetailRoute(
    val id: Int,
) : Screen {

    @Composable
    override fun Content() {
        DetailScreen(modifier = Modifier.fillMaxSize(), id)
    }
}