package com.towertex.tmdbmodel.services

import com.towertex.tmdbmodel.model.RowItem
import kotlinx.coroutines.flow.Flow

interface TrendingModelContract {
    fun trendingGet(mediaType: String, timeWindow: String, page: Int): Flow<List<RowItem>>
    fun trendingGet(id: Int?): Flow<RowItem?>
}