package id.tiooooo.pokedata.ui.pages.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import id.tiooooo.pokedata.data.api.model.PokemonItem

class DetailRoute(
    private val pokemonItem: PokemonItem,
) : Screen {

    @Composable
    override fun Content() {
        DetailScreen(
            modifier = Modifier.fillMaxSize(),
            pokemonItem = pokemonItem,
            homeScreenModel = koinScreenModel(),
            screenModel = koinScreenModel(),
        )
    }
}