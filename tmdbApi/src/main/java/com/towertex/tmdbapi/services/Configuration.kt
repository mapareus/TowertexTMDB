package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.data.ConfigurationResponse
import retrofit2.Call

interface Configuration {
    fun configurationGet(
        accessToken: String
    ): Call<ConfigurationResponse>
}