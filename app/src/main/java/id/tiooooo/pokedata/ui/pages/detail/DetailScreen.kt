package id.tiooooo.pokedata.ui.pages.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
) {
    Text("Ini adalah Id : $id")

}
