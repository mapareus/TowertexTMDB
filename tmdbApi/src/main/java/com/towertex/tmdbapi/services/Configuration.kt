package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.model.ConfigurationResponse
import retrofit2.Call

interface Configuration {
    fun configurationGet(): Call<ConfigurationResponse>
}