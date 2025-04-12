package id.tiooooo.pokedata.data.implementation.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.tiooooo.pokedata.data.implementation.local.entity.PokemonItemEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonItemEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()

    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun getPagedPokemon(): PagingSource<Int, PokemonItemEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :query || '%' ORDER BY id ASC")
    fun searchPokemonPaged(query: String): PagingSource<Int, PokemonItemEntity>

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun countPokemon(): Int

    @Query("UPDATE pokemon SET colorHex = :color WHERE id = :id")
    suspend fun updatePokemonColor(id: Int, color: String)
}
