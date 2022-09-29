package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.model.TrendingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendingServices {
    @GET("trending/{media_type}/{time_window}")
    fun get(
        @Path(value = "media_type", encoded = true) mediaType: String,
        @Path(value = "time_window", encoded = true) timeWindow: String,
        @Header("Authorization") authToken: String,
        @Query("page") page: Int
    ): Call<TrendingResponse>
}