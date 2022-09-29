package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.TMDBCall
import com.towertex.tmdbapi.model.ConfigurationResponse
import retrofit2.Call
import retrofit2.Retrofit

class ConfigurationApi(
    retrofit: Retrofit,
    val token: String
): Configuration {
    private val services = retrofit.create(ConfigurationServices::class.java)

    override fun configurationGet(): Call<ConfigurationResponse> = TMDBCall(
        services.get("Bearer $token")
    )
}