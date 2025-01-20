package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.TMDBApiResult
import com.towertex.tmdbapi.model.ConfigurationResponse

interface Configuration {
    suspend fun configurationGet(

    ): TMDBApiResult<ConfigurationResponse>
}