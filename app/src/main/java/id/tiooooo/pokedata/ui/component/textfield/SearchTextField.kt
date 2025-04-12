package id.tiooooo.pokedata.ui.component.textfield

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import id.tiooooo.pokedata.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.customTextFieldColors
import id.tiooooo.pokedata.ui.theme.textMedium12

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    label: String,
    searchQuery: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = searchQuery,

            textStyle = textMedium12().copy(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = { onValueChange.invoke(it) },
            shape = RoundedCornerShape(EXTRA_LARGE_PADDING),
            placeholder = {
                Text(
                    text = label,
                    style = textMedium12().copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    maxLines = 1,
                )
            },
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            colors = customTextFieldColors,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onValueChange.invoke("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
        )
    }
}