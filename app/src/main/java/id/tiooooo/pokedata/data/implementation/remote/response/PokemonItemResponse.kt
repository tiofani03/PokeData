package id.tiooooo.pokedata.data.implementation.remote.response

import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.implementation.local.entity.PokemonItemEntity
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_BASE_URL
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_DEFAULT_COLOR
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class PokemonItemResponse(
    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null,
)

@Serializable
data class ListResponse<T>(
    @SerialName("results") var data: T? = null,
    @SerialName("next") var next: String? = null,
    @SerialName("previous") var previous: String? = null,
)

fun PokemonItemResponse?.toPokemonItem(): PokemonItem = PokemonItem(
    id = this.createId(),
    name = this?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        .orEmpty(),
    image = this.createImageUrl()
)

fun PokemonItemResponse?.toPokemonEntity(): PokemonItemEntity = PokemonItemEntity(
    id = this.createId(),
    name = this?.name.orEmpty(),
    image = this.createImageUrl(),
    colorHex = IMAGE_DEFAULT_COLOR
)

fun PokemonItemResponse?.createId(): Int {
    val rawId = this?.url
        ?.trimEnd('/')
        ?.split("/")
        ?.lastOrNull()
        ?.toIntOrNull()

    return rawId ?: 0
}

fun PokemonItemResponse?.createImageUrl() = "$IMAGE_BASE_URL${this.createId()}.png"
