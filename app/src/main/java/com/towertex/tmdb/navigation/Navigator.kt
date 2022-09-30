package com.towertex.tmdb.navigation

import android.util.Log
import com.towertex.tmdb.fragments.BrowseFragment
import com.towertex.tmdb.fragments.DetailFragment

class Navigator: NavigatorContract {
    override var pendingNavigationTarget: NavigationTarget? = null
    private var navigatorContract: ActivityContract? = null

    override fun setActivityContract(contract: ActivityContract?) {
        Log.d("Navigator", "setActivityContract: $contract pending: $pendingNavigationTarget")
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
        Log.d("Navigator", "navigate: $target")
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