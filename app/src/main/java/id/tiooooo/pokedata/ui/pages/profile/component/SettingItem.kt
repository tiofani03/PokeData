package id.tiooooo.pokedata.ui.pages.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium10
import id.tiooooo.pokedata.ui.theme.textMedium14

@Composable
fun SettingItemLayout(
    item: SettingItem,
    onSettingClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable {
                onSettingClicked.invoke()
            }
            .fillMaxWidth()
            .padding(MEDIUM_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = item.title)
        Spacer(modifier = Modifier.width(MEDIUM_PADDING))

        Text(
            modifier = Modifier.weight(1f),
            text = item.title,
            style = textMedium14().copy(
                fontWeight = FontWeight.Light
            )
        )

        if (item.actionText != null) {
            Text(
                text = item.actionText,
                style = textMedium10().copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(MEDIUM_PADDING))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = MEDIUM_PADDING, vertical = SMALL_PADDING)
            )
        } else {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Next")
        }
    }
}

data class SettingItem(
    val title: String,
    val icon: ImageVector,
    val actionText: String? = null
)