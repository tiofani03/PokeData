package id.tiooooo.pokedata.data.implementation.remote.response

import com.google.gson.annotations.SerializedName
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.implementation.local.entity.PokemonItemEntity
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_BASE_URL
import id.tiooooo.pokedata.utils.AppConstants.IMAGE_DEFAULT_COLOR
import java.util.Locale

data class PokemonItemResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?,
)

data class ListResponse<T>(
    @SerializedName("results") var data: T? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null,
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
