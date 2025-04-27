package id.tiooooo.pokedata.ui.pages.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.api.model.createDisplayId
import id.tiooooo.pokedata.ui.pages.detail.component.DetailAbilitiesView
import id.tiooooo.pokedata.ui.pages.detail.component.DetailStatisticView
import id.tiooooo.pokedata.ui.pages.detail.component.DetailTitleAndTypeView
import id.tiooooo.pokedata.ui.pages.detail.component.DetailEvolutionView
import id.tiooooo.pokedata.ui.pages.home.HomeIntent
import id.tiooooo.pokedata.ui.pages.home.HomeScreenModel
import id.tiooooo.pokedata.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
                .verticalScroll(rememberScrollState()),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = bgColor.copy(0.7f),
                        shape = RoundedCornerShape(SMALL_PADDING)
                    ),
            ) {
                Spacer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .height(72.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(
                                topStart = MEDIUM_PADDING,
                                topEnd = MEDIUM_PADDING,
                            )
                        )
                        .fillMaxWidth()
                )
                AsyncImage(
                    model = pokemonItem.image,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(450.dp)
                        .padding(horizontal = MEDIUM_PADDING)
                        .padding(top = MEDIUM_PADDING)
                        .offset(y = MEDIUM_PADDING),
                    contentScale = ContentScale.Fit,
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally),
                )
            } else {
                if (state.pokemonDetail.description.isNotEmpty()) {
                    DetailTitleAndTypeView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING),
                        state = state,
                        abilitiesColor = abilitiesColor,
                        pokemonItem = pokemonItem,
                    )
                    DetailEvolutionView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MEDIUM_PADDING),
                        evolutionList = state.pokemonDetail.evolutionChain,
                        bgColor = bgColor.copy(0.7f)
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = MEDIUM_PADDING,
                            start = MEDIUM_PADDING,
                            end = MEDIUM_PADDING,
                        ),
                        text = state.pokemonDetail.description
                    )

                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                    DetailStatisticView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING),
                        state = state,
                        bgColor = bgColor,
                    )
                }

                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                DetailAbilitiesView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MEDIUM_PADDING),
                    state = state,
                    abilitiesColor = abilitiesColor,
                )
            }

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        }
    }
}
