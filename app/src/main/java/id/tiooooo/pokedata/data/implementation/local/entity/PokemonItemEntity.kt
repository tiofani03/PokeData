package id.tiooooo.pokedata.data.implementation.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.tiooooo.pokedata.data.api.model.PokemonItem

@Entity(tableName = "pokemon")
data class PokemonItemEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val colorHex: String,
)

fun PokemonItemEntity.toPokemonItem() = PokemonItem(
    id = this.id,
    name = this.name,
    image = this.image,
    hexColor = this.colorHex,
)