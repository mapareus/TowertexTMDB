package com.towertex.tmdbapi.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieListResultObject(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val id: Int,
    val title: String? = null,
    val original_language: String,
    val original_title: String? = null,
    val overview: String,
    val poster_path: String?,
    val media_type: String,
    val genre_ids: List<Int>,
    val popularity: Float,
    val release_date: String? = null,
    val video: Boolean = false,
    val vote_average: Float,
    val vote_count: Int,
    )