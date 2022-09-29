package com.towertex.tmdb.navigation

import androidx.fragment.app.Fragment
import com.towertex.tmdb.MainActivity
import com.towertex.tmdb.R

internal fun MainActivity.internalNavigate(fragmentFactory: () -> Fragment, location: NavigationLocation, tag: String?) {
    val transaction = supportFragmentManager.beginTransaction()
    val existingFragment = findFragment(location, tag)
    when {
        existingFragment == null -> {
            transaction
                .replace(location.containerId, fragmentFactory(), tag)
        }
        existingFragment.isHidden -> {
            //while there is only one container in the demo app the fragment can never get hidden
            transaction
                .show(existingFragment)
        }
        else -> { } //don't do anything
    }
    transaction.addToBackStack(null)
    transaction.commit()
}

private fun MainActivity.findFragment(navigationLocation: NavigationLocation, tag: String?): Fragment? {
    val fragment = supportFragmentManager.findFragmentById(navigationLocation.containerId)
    if (fragment != null && fragment.tag == tag) return fragment
    return null
}

//This is the only intended place where containers are identified. The id should never be used outside of here
private val NavigationLocation.containerId: Int
    get() = when (this) {
        NavigationLocation.FULLSCREEN -> R.id.fullscreen_container
    }