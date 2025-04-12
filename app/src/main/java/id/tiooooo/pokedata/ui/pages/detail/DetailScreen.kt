package id.tiooooo.pokedata.ui.pages.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.api.model.createDisplayId
import id.tiooooo.pokedata.data.api.model.createDisplayName
import id.tiooooo.pokedata.ui.pages.detail.component.DetailAppBar
import id.tiooooo.pokedata.ui.pages.home.HomeIntent
import id.tiooooo.pokedata.ui.pages.home.HomeScreenModel
import id.tiooooo.pokedata.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium18
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_DEFAULT_COLOR
import id.tiooooo.pokedata.utils.rememberImageDominantColor

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    pokemonItem: PokemonItem,
    screenModel: DetailScreenModel,
    homeScreenModel: HomeScreenModel,
    context: Context = LocalContext.current,
) {
    val navigator = LocalNavigator.currentOrThrow
    val bgColor = if (pokemonItem.hexColor == IMAGE_DEFAULT_COLOR) {
        rememberImageDominantColor(
            id = pokemonItem.createDisplayId(),
            imageUrl = pokemonItem.image,
            context = context,
            onColorExtracted = {
                homeScreenModel.dispatch(HomeIntent.UpdateColor(pokemonItem.id, it))
            }
        )
    } else {
        Color(pokemonItem.hexColor.toColorInt())
    }
    val abilitiesColor = bgColor.copy(0.2f)

    val state by screenModel.state.collectAsState()

    LaunchedEffect(pokemonItem) {
        screenModel.dispatch(DetailIntent.GetPokemonDetail(pokemonItem))
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            DetailAppBar(
                title = pokemonItem.createDisplayName(),
                subtitle = pokemonItem.createDisplayId(),
            ) { navigator.pop() }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(MEDIUM_PADDING)
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = bgColor.copy(0.7f),
                        shape = RoundedCornerShape(SMALL_PADDING)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = pokemonItem.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(MEDIUM_PADDING),
                    contentScale = ContentScale.Fit,
                )
            }

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Description",
                fontWeight = FontWeight.Bold,
                style = textMedium18(),
            )
            Text(
                text = state.pokemonDetail.description,
                modifier = Modifier.padding(top = SMALL_PADDING)
            )

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Abilities",
                fontWeight = FontWeight.Bold,
                style = textMedium18(),
            )
            Row(
                modifier = Modifier
                    .padding(top = SMALL_PADDING)
                    .fillMaxWidth()
            ) {
                state.pokemonDetail.abilities.forEach {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(MEDIUM_PADDING))
                            .background(color = abilitiesColor)
                            .padding(horizontal = MEDIUM_PADDING, vertical = SMALL_PADDING)
                    ) {
                        Text(
                            text = it.replaceFirstChar { c -> c.uppercaseChar() },
                        )
                    }
                    Spacer(Modifier.width(SMALL_PADDING))
                }
            }
        }
    }
}
