package id.tiooooo.pokedata.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.R
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.data.api.model.createDisplayId
import id.tiooooo.pokedata.ui.component.BasicTopBarTitle
import id.tiooooo.pokedata.ui.component.textfield.SearchTextField
import id.tiooooo.pokedata.ui.pages.detail.DetailRoute
import id.tiooooo.pokedata.ui.pages.home.component.HomeErrorMessage
import id.tiooooo.pokedata.ui.pages.home.component.PokemonCard
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium14
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_DEFAULT_COLOR

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    screenModel: HomeScreenModel,
) {
    val pagingItems = screenModel.pokemonFlow.collectAsLazyPagingItems()
    val isEmpty =
        pagingItems.itemCount == 0 && pagingItems.loadState.refresh is LoadState.NotLoading

    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        screenModel.effect.collect{ effect ->
            when(effect){
                is HomeEffect.NavigateToDetail -> {
                    navigator.push(DetailRoute(effect.id))
                }
            }
        }
    }

    BaseScaffold(
        modifier = modifier,
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            state = state.listState
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier.padding(horizontal = MEDIUM_PADDING)
                ) {
                    BasicTopBarTitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        title = stringResource(R.string.app_name),
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Ayo cari dan temukan bersama, kamu juga bisa cari berdasarkan nama",
                        style = textMedium14().copy(
                            fontWeight = FontWeight.Light,
                        )
                    )
                }
            }
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                SearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MEDIUM_PADDING),
                    label = "Cari Pokemon",
                    searchQuery = state.searchQuery,
                    onValueChange = { screenModel.dispatch(HomeIntent.UpdateSearchQuery(it)) },
                )
            }
            if (isEmpty) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Data tidak ditemukan",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                items(pagingItems.itemCount) { index ->
                    val data = pagingItems[index]
                    if (data != null) {
                        PokemonCard(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    screenModel.dispatch(HomeIntent.NavigateToDetail(data.id))
                                },
                            name = data.name,
                            id = data.createDisplayId(),
                            image = data.image,
                            hexColor = data.hexColor
                        ) {
                            if (data.hexColor == IMAGE_DEFAULT_COLOR) {
                                screenModel.dispatch(HomeIntent.UpdateColor(data.id, it))
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(100.dp)
                                .fillMaxWidth()
                                .background(Color.LightGray, RoundedCornerShape(8.dp))
                        )
                    }
                }
            }

            pagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val e = loadState.refresh as LoadState.Error
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            HomeErrorMessage(
                                message = e.error.localizedMessage ?: "Unknown Error",
                                onRetry = { pagingItems.retry() }
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val e = loadState.append as LoadState.Error
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            HomeErrorMessage(
                                message = e.error.localizedMessage ?: "Failed to load more",
                                onRetry = { pagingItems.retry() }
                            )
                        }
                    }
                }
            }
        }
    }
}
