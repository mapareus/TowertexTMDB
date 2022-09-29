package com.towertex.tmdbapi.model

class TMDBApiException(
    val httpCode: Int,
    val httpMessage: String,
    val errorBody: TMDBError?,
    val serviceUrl: String
) : RuntimeException()