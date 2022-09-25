package com.towertex.tmdbapi.data

class TMDBApiException(
    val httpCode: Int,
    val httpMessage: String,
    val errorBody: TMDBError?,
    val serviceUrl: String
) : RuntimeException()