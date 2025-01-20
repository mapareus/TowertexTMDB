package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.TMDBApiResult
import com.towertex.tmdbapi.model.TrendingResponse

interface Trending {

    @Suppress("unused")
    enum class MediaType(val value: String) {
        ALL("all"),
        MOVIE("movie"),
        TV("tv"),
        PERSON("person")
    }

    @Suppress("unused")
    enum class TimeWindow(val value: String) {
        DAY("day"),
        WEEK("week")
    }

    suspend fun trendingGet(
        mediaType: MediaType,
        timeWindow: TimeWindow,
        page: Int = 1
    ): TMDBApiResult<TrendingResponse>
}