package id.tiooooo.pokedata.ui.pages.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.api.model.createDisplayName
import id.tiooooo.pokedata.ui.pages.detail.DetailState
import id.tiooooo.pokedata.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.pokeColors
import id.tiooooo.pokedata.ui.theme.textMedium12
import id.tiooooo.pokedata.ui.theme.textMedium24

@Composable
fun DetailTitleAndTypeView(
    modifier: Modifier = Modifier,
    state: DetailState,
    pokemonItem: PokemonItem,
    abilitiesColor: Color,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(EXTRA_SMALL_PADDING)
    ) {
        Text(
            modifier = Modifier,
            text = pokemonItem.createDisplayName(),
            fontWeight = FontWeight.Bold,
            style = textMedium24().copy(
                fontSize = 32.sp
            ),
        )
        state.pokemonDetail.types.forEach { type ->
            val typeColor = pokeColors[type.lowercase()] ?: abilitiesColor
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(MEDIUM_PADDING))
                    .background(color = typeColor)
                    .padding(
                        horizontal = SMALL_PADDING,
                        vertical = EXTRA_SMALL_PADDING
                    )
            ) {
                Text(
                    text = type.replaceFirstChar { c -> c.uppercaseChar() },
                    style = textMedium12(),
                )
            }
        }
    }
}