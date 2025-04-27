package id.tiooooo.pokedata.ui.pages.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import id.tiooooo.pokedata.ui.pages.detail.DetailState
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium18
import id.tiooooo.pokedata.utils.localization.stringRes

@Composable
fun DetailAbilitiesView(
    modifier: Modifier = Modifier,
    state: DetailState,
    abilitiesColor : Color,
) {
    Column(modifier = modifier) {
        if (state.pokemonDetail.abilities.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringRes("abilities"),
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