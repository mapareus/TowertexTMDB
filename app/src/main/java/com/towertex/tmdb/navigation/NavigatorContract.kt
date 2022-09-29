package com.towertex.tmdb.navigation

import com.towertex.tmdb.model.NavigationTarget

interface NavigatorContract {
    fun setActivityContract(contract: ActivityContract?)
    fun navigate(target: NavigationTarget)
}