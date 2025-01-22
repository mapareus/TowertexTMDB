package com.towertex.tmdbapi.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status_message: String,
    val success: Boolean,
    val status_code: Int
)