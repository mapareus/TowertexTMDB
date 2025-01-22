package com.towertex.tmdbapi.services

import com.towertex.tmdbapi.model.ConfigurationResponse
import com.towertex.tmdbapi.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.url

class ConfigurationApi(
    private val httpClient: HttpClient,
    private val token: String,
    private val baseUrl: String
): Configuration {
    override suspend fun configurationGet(

    ) = httpClient.toResult<ConfigurationResponse> {
        get {
            url(baseUrl + "configuration")
            bearerAuth(token)
        }
    }
}