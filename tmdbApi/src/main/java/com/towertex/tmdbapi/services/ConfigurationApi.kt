package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.TMDBCall
import com.towertex.tmdbapi.data.ConfigurationResponse
import retrofit2.Call
import retrofit2.Retrofit

class ConfigurationApi(
    retrofit: Retrofit
): Configuration {
    private val services = retrofit.create(ConfigurationServices::class.java)

    override fun configurationGet(accessToken: String): Call<ConfigurationResponse> = TMDBCall(
        services.get("Bearer $accessToken")
    )
}