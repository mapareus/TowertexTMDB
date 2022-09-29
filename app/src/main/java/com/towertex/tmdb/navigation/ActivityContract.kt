package com.towertex.tmdb.navigation

import androidx.fragment.app.Fragment

// This is meant to be implemented only by Activity and there should not be calls which do not need it
interface ActivityContract {
    fun navigate(fragmentFactory: () -> Fragment, location: NavigationLocation, tag: String? = null)
}