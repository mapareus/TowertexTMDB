package com.towertex.tmdb.model

data class NavigationTarget(
    val action: NavigationAction,
    val itemId: Int? = null
)