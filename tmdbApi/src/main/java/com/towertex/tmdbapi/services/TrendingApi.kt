package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.TMDBCall
import com.towertex.tmdbapi.data.TrendingResponse
import retrofit2.Call
import retrofit2.Retrofit

class TrendingApi(
    retrofit: Retrofit
): Trending {
    private val services = retrofit.create(TrendingServices::class.java)

    override fun trendingGet(mediaType: String, timeWindow: String, accessToken: String, page: Int): Call<TrendingResponse> = TMDBCall(
        services.get(mediaType, timeWindow, "Bearer $accessToken", page)
    )
}