package com.towertex.tmdb.viewModels

import androidx.lifecycle.ViewModel
import com.towertex.tmdb.model.NavigationAction
import com.towertex.tmdb.model.NavigationTarget
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