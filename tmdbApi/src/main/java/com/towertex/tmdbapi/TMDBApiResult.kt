package com.towertex.tmdbapi

import com.towertex.tmdbapi.model.ErrorResponse
import com.towertex.tmdbapi.TMDBApiErrorType.NO_INTERNET
import com.towertex.tmdbapi.TMDBApiErrorType.SERIALIZATION
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.toResult(
    getResponseBlock: HttpClient.() -> HttpResponse
): TMDBApiResult<T> =
    try {
        getResponseBlock().run {
            status.value.let {
                when (it) {
                    in 200..299 -> Success(body<T>())
                    else -> Error(TMDBApiErrorType.fromHttpCode(it), it, body())
                }
            }
        }
    } catch(e: UnresolvedAddressException) {
        Exception(NO_INTERNET)
    } catch(e: SerializationException) {
        Exception(SERIALIZATION)
    }

sealed interface TMDBApiResult<out D>
data class Success<out D>(val data: D): TMDBApiResult<D>
data class Error<out D>(val apiErrorType: TMDBApiErrorType, val httpCode: Int, val error: ErrorResponse): TMDBApiResult<D>
data class Exception<out D>(val apiErrorType: TMDBApiErrorType): TMDBApiResult<D>