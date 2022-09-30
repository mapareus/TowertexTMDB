package com.towertex.tmdb.navigation

interface NavigatorContract {
    var pendingNavigationTarget: NavigationTarget?
    fun setActivityContract(contract: ActivityContract?)
    fun navigate(target: NavigationTarget)
}