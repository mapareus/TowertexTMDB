package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.data.ConfigurationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ConfigurationServices {
    @GET("configuration")
    fun get(
        @Header("Authorization") authToken: String
    ): Call<ConfigurationResponse>
}