package com.towertex.tmdbapi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TMDBApiBuilder @JvmOverloads constructor (
    private val endpoint: String,
    private val httpLoggingInterceptor: HttpLoggingInterceptor = defaultHttpLoggingInterceptor
) {
    companion object {

        private val defaultHttpLoggingInterceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        private val gson: Gson
            get() = GsonBuilder().create()
    }

    private fun getHttpClient(
        httpLoggingInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .build()

    private fun buildRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(endpoint)
        .client(getHttpClient(httpLoggingInterceptor))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun build(): TMDBApi = TMDBApi(buildRetrofit())
}