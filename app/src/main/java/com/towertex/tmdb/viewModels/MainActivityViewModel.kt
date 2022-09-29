package com.towertex.tmdb.viewModels

import androidx.lifecycle.ViewModel
import com.towertex.tmdb.navigation.NavigationAction
import com.towertex.tmdb.navigation.NavigationTarget
import com.towertex.tmdb.navigation.NavigatorContract

class MainActivityViewModel(
    private val navigator: NavigatorContract
) : ViewModel() {

    fun onCreate() {
        navigator.navigate(
            target = NavigationTarget(
                action = NavigationAction.BROWSE
            )
        )
    }
}