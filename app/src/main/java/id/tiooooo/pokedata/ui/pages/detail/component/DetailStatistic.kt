package id.tiooooo.pokedata.ui.pages.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import id.tiooooo.pokedata.ui.pages.detail.DetailState
import id.tiooooo.pokedata.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.utils.localization.stringRes


@Composable
fun DetailStatisticView(
    modifier: Modifier = Modifier,
    state: DetailState,
    bgColor: Color,
) {
    Column(modifier = modifier) {
        state.pokemonDetail.stats.forEach { stat ->
            ItemStatistic(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(EXTRA_SMALL_PADDING),
                text = stat.name,
                currentValue = stat.baseStat.toDouble(),
                maxValue = 200.0,
                progressIndicatorColor = bgColor,
            )
        }
    }
}

@Composable
fun ItemStatistic(
    modifier: Modifier = Modifier,
    text: String,
    currentValue: Double,
    maxValue: Double,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(0.4f),
            text = stringRes(text),
            fontSize = MaterialTheme.typography.labelMedium.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        CustomLinearProgressIndicator(
            progress = (currentValue.toFloat() / maxValue.toFloat()).coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .height(MEDIUM_PADDING)
                .weight(0.6f)
                .padding(
                    vertical = EXTRA_SMALL_PADDING,
                    horizontal = SMALL_PADDING,
                ),
            backgroundColor = progressIndicatorColor.copy(alpha = 0.3f),
            progressColor = progressIndicatorColor
        )
    }
}


@Composable
fun CustomLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    progressColor: Color = Color.Blue,
) {
    Box(
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(progressColor)
        )
    }
}