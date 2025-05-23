package id.tiooooo.pokedata.ui.component

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatedPreloader(
    @RawRes animationRes: Int,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever,
    isPlaying: Boolean = true,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = iterations,
        isPlaying = isPlaying
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}
