package id.tiooooo.pokedata.ui.pages.home.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium12
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_DEFAULT_COLOR
import id.tiooooo.pokedata.utils.rememberImageDominantColor
import androidx.core.graphics.toColorInt

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    name: String,
    id: String,
    image: String,
    hexColor: String,
    context: Context = LocalContext.current,
    onColorExtracted: (String) -> Unit = {},
) {
    val bgColor = if (hexColor == IMAGE_DEFAULT_COLOR) {
        rememberImageDominantColor(
            id = id,
            imageUrl = image,
            context = context,
            onColorExtracted = onColorExtracted
        )
    } else {
        Color(hexColor.toColorInt())
    }

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
                shape = RoundedCornerShape(SMALL_PADDING)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(MEDIUM_PADDING),
            contentScale = ContentScale.Fit,
        )

        Text(
            text = name,
            modifier = Modifier.padding(horizontal = SMALL_PADDING),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = id,
            modifier = Modifier
                .padding(horizontal = SMALL_PADDING)
                .padding(bottom = MEDIUM_PADDING),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = textMedium12(),
            fontWeight = FontWeight.Light
        )
    }
}