package com.towertex.tmdbmodel.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.towertex.tmdbapi.model.MovieListResultObject
import com.towertex.tmdbmodel.model.RowItem
import com.towertex.tmdbmodel.room.TMDBDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.reflect.Type

class TrendingModel(
    private val api: TMDBApi,
    private val dao: TMDBDao
) : TrendingModelContract {

    companion object {
        private val type: Type = object : TypeToken<List<Int?>?>() {}.type
    }

    override fun trendingGet(mediaType: String, timeWindow: String, page: Int): Flow<List<RowItem>> = flow {
        val currentData = dao.getPage(page)
        if (currentData.isNotEmpty()) emit(currentData)
        val newData = api.trendingGet(mediaType, timeWindow, page).execute().body()?.results ?: emptyList()
        if (newData.isEmpty()) {
            if (currentData.isNotEmpty()) return@flow
            emit(emptyList())
            return@flow
        }
        if (currentData.equals(newData, page)) return@flow
        val transformedNewData = newData.map { it.toRowItem(page) }
        dao.deleteItems(currentData.map { it.id })
        dao.insertItems(transformedNewData)
        emit(transformedNewData)
    }

    private fun List<RowItem>.equals(new: List<MovieListResultObject>, page: Int): Boolean {
        if (size != new.size) return false
        sortedBy { it.id }.zip(new.sortedBy { it.id }).forEach {
            if (it.first.page != page) return false
            if (it.first.id != it.second.id) return false
        }
        return true
    }

    private fun MovieListResultObject.toRowItem(page: Int) = RowItem(
        page,
        poster_path,
        adult,
        overview,
        release_date,
        Gson().toJson(genre_ids, type),
        id,
        original_title,
        original_language,
        title,
        backdrop_path,
        popularity,
        vote_count,
        video,
        vote_average
    )

    override fun trendingGet(id: Int?): Flow<RowItem?> = flow {
        emit(id?.let { dao.getItem(it).firstOrNull() })
    }
}