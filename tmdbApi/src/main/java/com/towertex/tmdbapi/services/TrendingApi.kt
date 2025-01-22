package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.model.TrendingResponse
import com.towertex.tmdbapi.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.url

class TrendingApi(
    private val httpClient: HttpClient,
    private val token: String,
    private val baseUrl: String
): Trending {
    override suspend fun trendingGet(
        mediaType: Trending.MediaType,
        timeWindow: Trending.TimeWindow,
        page: Int
    ) = httpClient.toResult<TrendingResponse> {
        get {
            url("${baseUrl}trending/${mediaType.value}/${timeWindow.value}?page=$page")
            bearerAuth(token)
        }
    }
}