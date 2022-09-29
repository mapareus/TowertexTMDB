package com.towertex.tmdb.adapters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.towertex.tmdbapi.services.Trending
import com.towertex.tmdbmodel.TMDBModel
import com.towertex.tmdbmodel.model.RowItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class RowPagingSource(
    private val model: TMDBModel
) : PagingSource<Int, RowItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RowItem> {
        return try {
            val nextPageNumber: Int = params.key ?: 0
            val response = withContext(Dispatchers.IO) { model.trendingGet(Trending.MEDIA_TYPE_MOVIE, Trending.TIME_WINDOW_WEEK, nextPageNumber+1).first() }
            //TODO Timber
            Log.d("data", "$nextPageNumber ... ${response.size}")
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = nextPageNumber + 1 //TODO handle last page
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RowItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val pageIndex = anchorPosition / 20 //TODO compute page size
        return pageIndex
    }
}