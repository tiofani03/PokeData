package id.tiooooo.pokedata.ui.pages.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.tiooooo.pokedata.ui.component.bottomNavigation.BottomNavItem
import id.tiooooo.pokedata.ui.component.bottomNavigation.BottomNavModel
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING

@Composable
fun BottomNavDashboard(
    modifier: Modifier = Modifier,
    bottomNavList: List<BottomNavModel>,
    bottomSelectedItem: String,
    onBottomNavClicked: (String) -> Unit

    ) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = MEDIUM_PADDING,
            topEnd = MEDIUM_PADDING
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomNavList.forEachIndexed { _, bottomNavModel ->
                BottomNavItem(
                    modifier = Modifier.weight(1f),
                    bottomNavModel = bottomNavModel,
                    isSelected = bottomSelectedItem == bottomNavModel.slug
                ) {
                    onBottomNavClicked.invoke(bottomNavModel.slug)
                }
            }
        }
    }
}