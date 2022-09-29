package com.towertex.tmdb.navigation

import com.towertex.tmdb.fragments.BrowseFragment
import com.towertex.tmdb.fragments.DetailFragment
import com.towertex.tmdb.model.NavigationAction
import com.towertex.tmdb.model.NavigationTarget

class Navigator: NavigatorContract {
    private var pendingNavigationTarget: NavigationTarget? = null
    private var navigatorContract: ActivityContract? = null

    override fun setActivityContract(contract: ActivityContract?) {
        navigatorContract = contract
        pendingNavigationTarget?.also { navigate(it) }
    }

    override fun navigate(target: NavigationTarget) {
        pendingNavigationTarget = navigatorContract
            ?.let {
                it.navigate(target)
                null
            }
            ?: target
    }

    private fun ActivityContract.navigate(target: NavigationTarget) {
        when (target.action) {
            NavigationAction.BROWSE -> {
                navigate(
                    fragmentFactory = { BrowseFragment() },
                    location = NavigationLocation.FULLSCREEN,
                    tag = NavigationAction.BROWSE.name
                )
            }
            NavigationAction.DETAIL -> {
                navigate(
                    fragmentFactory = { DetailFragment(target.itemId) },
                    location = NavigationLocation.FULLSCREEN,
                    tag = target.itemId.toString()
                )
            }
        }
    }
}