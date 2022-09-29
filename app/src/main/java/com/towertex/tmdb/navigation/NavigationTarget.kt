package com.towertex.tmdb.navigation

data class NavigationTarget(
    val action: NavigationAction,
    val itemId: Int? = null
)