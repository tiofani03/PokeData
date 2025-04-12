package id.tiooooo.pokedata.data.implementation.remote.pagingsource

import androidx.core.net.toUri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.tiooooo.pokedata.data.api.model.PokemonItem
import id.tiooooo.pokedata.data.implementation.remote.response.toPokemonItem
import id.tiooooo.pokedata.data.implementation.remote.service.PokeService

class PokemonPagingSource(
    private val service: PokeService,
    private val query: String?
) : PagingSource<Int, PokemonItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonItem> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize

            val response = service.getPokemon(limit = limit, offset = offset)

            val allData = response.data?.map { it.toPokemonItem() } ?: emptyList()
            val filteredData = if (query.isNullOrEmpty()) {
                allData
            } else {
                allData.filter {
                    it.name.contains(query.trim(), ignoreCase = true)
                }
            }
            val nextOffset = getOffsetFromUrl(response.next)
            val prevOffset = if (offset == 0) null else maxOf(0, offset - limit)

            LoadResult.Page(
                data = filteredData,
                prevKey = prevOffset,
                nextKey = nextOffset
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    private fun getOffsetFromUrl(url: String?): Int? {
        return url?.let { it.toUri().getQueryParameter("offset")?.toIntOrNull() }
    }

}

