package com.towertex.tmdbapi

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.towertex.tmdbapi.data.TMDBApiException
import com.towertex.tmdbapi.data.TMDBError
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class TMDBCall<TResponse>(private val original: Call<TResponse>) : Call<TResponse> {

    private fun readJsonBody(responseBody: ResponseBody?): TMDBError? = when {
        responseBody?.contentType().isJsonMediaType() -> {
            try {
                Gson().fromJson(responseBody!!.charStream(), TMDBError::class.java)
            } catch (e: JsonParseException) {
                null
            }
        }
        else -> null
    }

    private fun MediaType?.isJsonMediaType(): Boolean = this != null && type == "application" && subtype == "json"

    @Throws(IOException::class)
    override fun execute(): Response<TResponse> = transformResponse(original.execute())

    private fun transformResponse(response: Response<TResponse>): Response<TResponse> = when {
        response.isSuccessful -> response
        else -> {
            val tmdbError = readJsonBody(response.errorBody())
            throw TMDBApiException(
                response.code(),
                response.message() ?: "HTTP ${response.code()}",
                tmdbError,
                original.request().url.encodedPath
            )
        }
    }

    override fun enqueue(callback: Callback<TResponse>) {
        original.enqueue(object : Callback<TResponse> {
            override fun onResponse(call: Call<TResponse>, response: Response<TResponse>) {
                val transformed: Response<TResponse>
                try {
                    transformed = transformResponse(response)
                } catch (t: Throwable) {
                    callback.onFailure(this@TMDBCall, t)
                    return
                }
                callback.onResponse(this@TMDBCall, transformed)
            }

            override fun onFailure(call: Call<TResponse>, t: Throwable) {
                callback.onFailure(this@TMDBCall, t)
            }
        })
    }

    override fun isExecuted(): Boolean = original.isExecuted

    override fun cancel() { original.cancel() }

    override fun isCanceled(): Boolean = original.isCanceled

    override fun clone(): Call<TResponse> = TMDBCall(original.clone())

    override fun request(): Request = original.request()

    override fun timeout(): Timeout = original.timeout()
}