package id.tiooooo.pokedata.ui.pages.detail.component

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import id.tiooooo.pokedata.data.api.model.EvolutionChain
import id.tiooooo.pokedata.ui.theme.LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING

@Composable
fun DetailEvolutionView(
    modifier: Modifier = Modifier,
    evolutionList: List<EvolutionChain> = emptyList(),
    bgColor: Color = MaterialTheme.colorScheme.primary,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
        horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(evolutionList) { item ->
            MovieVideoItem(
                evolution = item,
                bgColor = bgColor,
            )
        }
    }
}

@Composable
fun MovieVideoItem(
    evolution: EvolutionChain,
    bgColor: Color,
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(SMALL_PADDING))
            .background(bgColor)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(evolution.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            loading = {
                AnimatedShimmerItemView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            },
            error = {
                AnimatedShimmerItemView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        )
    }
}

@Composable
fun AnimatedShimmerItemView(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = LARGE_PADDING,
    shimmerHeight: Dp = 150.dp,
) {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    ShimmerItemView(
        modifier = modifier,
        alpha = alphaAnim,
        cornerRadius = cornerRadius,
        shimmerHeight = shimmerHeight,
    )
}

@Composable
fun ShimmerItemView(
    alpha: Float,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = LARGE_PADDING,
    shimmerHeight: Dp,
) {
    val baseColor = MaterialTheme.colorScheme.surfaceVariant
    val highlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)

    Surface(
        modifier = modifier
            .alpha(alpha)
            .fillMaxWidth()
            .height(shimmerHeight),
        color = highlightColor,
        shape = RoundedCornerShape(cornerRadius)
    ) {}
}