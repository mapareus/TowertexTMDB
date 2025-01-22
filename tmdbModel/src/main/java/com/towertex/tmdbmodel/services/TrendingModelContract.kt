package com.towertex.tmdbmodel.services

import com.towertex.tmdbapi.services.Trending
import com.towertex.tmdbmodel.model.RowItem
import kotlinx.coroutines.flow.Flow

interface TrendingModelContract {
    fun trendingGet(mediaType: Trending.MediaType, timeWindow: Trending.TimeWindow, page: Int): Flow<List<RowItem>>
    fun trendingGet(id: Int?): Flow<RowItem?>
}