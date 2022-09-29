package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.TMDBCall
import com.towertex.tmdbapi.model.TrendingResponse
import retrofit2.Call
import retrofit2.Retrofit

class TrendingApi(
    retrofit: Retrofit,
    val token: String
): Trending {
    private val services = retrofit.create(TrendingServices::class.java)

    override fun trendingGet(mediaType: String, timeWindow: String, page: Int): Call<TrendingResponse> = TMDBCall(
        services.get(mediaType, timeWindow, "Bearer $token", page)
    )
}