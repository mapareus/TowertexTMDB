package com.towertex.tmdb.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.towertex.tmdb.navigation.NavigationAction
import com.towertex.tmdb.navigation.NavigationTarget
import com.towertex.tmdb.navigation.NavigatorContract

class MainActivityViewModel(
    private val navigator: NavigatorContract
) : ViewModel() {

    fun onCreate() {
        Log.d("Navigator", "MainActivityViewModel onCreate")
        navigator.navigate(
            target = navigator.pendingNavigationTarget ?: NavigationTarget(
                action = NavigationAction.BROWSE
            )
        )
    }
}