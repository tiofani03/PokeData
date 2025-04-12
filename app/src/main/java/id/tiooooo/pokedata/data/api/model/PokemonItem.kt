package id.tiooooo.pokedata.data.api.model

import id.tiooooo.pokedata.utils.AppConstants.IMAGE_DEFAULT_COLOR
import java.util.Locale

data class PokemonItem(
    val id: Int,
    val name: String,
    val image: String,
    val hexColor: String = IMAGE_DEFAULT_COLOR,
)

fun PokemonItem.createDisplayId() = String.format(Locale.getDefault(), "%03d", id)
