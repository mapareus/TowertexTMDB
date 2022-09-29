package com.towertex.tmdb.navigation

interface NavigatorContract {
    fun setActivityContract(contract: ActivityContract?)
    fun navigate(target: NavigationTarget)
}