package com.towertex.tmdbapi

import com.towertex.tmdbapi.services.Configuration
import com.towertex.tmdbapi.services.ConfigurationApi
import com.towertex.tmdbapi.services.Trending
import com.towertex.tmdbapi.services.TrendingApi
import io.ktor.client.HttpClient

//server api is typically divided into groups of services, here each group is implemented in separate delegate
class TMDBApi internal constructor(
    httpClient: HttpClient,
    token: String,
    baseUrl: String
):
    Trending by TrendingApi(httpClient, token, baseUrl),
    Configuration by ConfigurationApi(httpClient, token, baseUrl)
