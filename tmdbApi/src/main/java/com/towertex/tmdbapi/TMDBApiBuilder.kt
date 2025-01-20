package com.towertex.tmdbapi

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TMDBApiBuilder(
    private val token: String,
    private val baseUrl: String,
    private val logLevel: LogLevel,
    private val logger: Logger? = null
) {
    fun build(): TMDBApi = TMDBApi(getClient(logLevel, logger), token, baseUrl)

    private fun getClient(
        aLogLevel: LogLevel,
        aLogger: Logger?
    ): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = aLogLevel
                aLogger?.also { logger = it }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
//                    explicitNulls = false //TODO upgrade to KTor 3.0.3
                    }
                )
            }

        }
    }
}