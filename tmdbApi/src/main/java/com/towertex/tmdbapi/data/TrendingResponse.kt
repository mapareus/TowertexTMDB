package com.towertex.tmdbapi.data

data class TrendingResponse(
    val page: Int,
    val results: List<MovieListResultObject>,
    val total_pages: Int,
    val total_results: Int
)