package com.towertex.tmdbapi.data

data class TMDBError(
    val status_message: String,
    val error_message: String?,
    val success: Boolean,
    val status_code: Int
)