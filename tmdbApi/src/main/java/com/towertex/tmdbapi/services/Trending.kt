package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.data.TrendingResponse
import retrofit2.Call

@Suppress("unused")
interface Trending {
    companion object {
        const val MEDIA_TYPE_ALL = "all"
        const val MEDIA_TYPE_MOVIE = "movie"
        const val MEDIA_TYPE_tv = "tv"
        const val MEDIA_TYPE_PERSON = "person"
        const val TIME_WINDOW_DAY = "day"
        const val TIME_WINDOW_WEEK = "week"
    }

    fun trendingGet(
        mediaType: String,
        timeWindow: String,
        accessToken: String,
        page: Int = 1
    ): Call<TrendingResponse>
}