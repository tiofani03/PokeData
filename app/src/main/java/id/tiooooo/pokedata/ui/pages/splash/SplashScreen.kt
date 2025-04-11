package id.tiooooo.pokedata.ui.pages.splash

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import id.tiooooo.pokedata.R
import id.tiooooo.pokedata.ui.pages.login.LoginRoute
import id.tiooooo.pokedata.utils.ColorCache
import id.tiooooo.pokedata.utils.PaletteGenerator

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    screenModel: SplashScreenModel
) {
    val userList by screenModel.pokemonList.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        screenModel.executeUsers()
    }
    Box(modifier = modifier) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.app_name),
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { navigator.replace(LoginRoute()) }
            ) {
                Text(
                    text = "Buat akun ngawur"
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(userList) { data ->
                    val rawId = data.url
                        ?.trimEnd('/')
                        ?.split("/")
                        ?.lastOrNull()
                        ?.toIntOrNull()

                    val idText = String.format("%03d", rawId ?: 0)
                    val imageUrl =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$rawId.png"

                    PokemonCard(
                        modifier = Modifier.padding(8.dp),
                        name = data.name.orEmpty().replaceFirstChar { it.uppercaseChar() },
                        id = idText,
                        image = imageUrl
                    )
                }
            }
        }
    }

}


@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    name: String,
    id: String,
    image: String,
    context: Context = LocalContext.current,
) {
    val bgColor = rememberImageDominantColor(id, image, context)

    PokemonCardLayout(
        modifier = modifier,
        name = name,
        id = id,
        image = image,
        bgColor = bgColor
    )
}

@Composable
private fun PokemonCardLayout(
    modifier: Modifier,
    name: String,
    id: String,
    image: String,
    bgColor: Color
) {
    Column(
        modifier = modifier
            .background(
                color = bgColor.copy(0.7f),
                shape = RoundedCornerShape(8.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp),
            contentScale = ContentScale.Fit,
        )

        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = id,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun rememberImageDominantColor(
    id: String,
    imageUrl: String,
    context: Context
): Color {
    var bgColor by remember(imageUrl) { mutableStateOf(Color.Gray) }

    LaunchedEffect(imageUrl) {
        val cached = ColorCache.getColor(id)
        if (cached != null) {
            bgColor = Color(cached)
        } else {
            val bitmap = PaletteGenerator.convertImageUrlToBitmap(imageUrl, context)
            bitmap?.let {
                val hexColor = PaletteGenerator.extractBestSwatchColor(it)
                val intColor = hexColor.toColorInt()
                ColorCache.setColor(id, intColor)
                bgColor = Color(intColor)
            }
        }
    }

    return bgColor
}

