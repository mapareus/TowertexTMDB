package com.towertex.tmdb.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.towertex.tmdb.R
import com.towertex.tmdb.navigation.NavigationAction
import com.towertex.tmdb.navigation.NavigationTarget
import com.towertex.tmdb.navigation.NavigatorContract
import com.towertex.tmdb.repositories.ResourceRepositoryContract
import com.towertex.tmdbmodel.model.RowItem

class RowItemViewModel(
    resourceRepository: ResourceRepositoryContract,
    navigator: NavigatorContract,
    item: RowItem
) : ViewModel() {
    val minHeight: Int = resourceRepository.getDimensionPixelSize(R.dimen.item_min_height)
    val spacing: Int = resourceRepository.getDimensionPixelSize(R.dimen.default_margin)
    val textColor: Int = resourceRepository.getColor(R.color.white)
    val title: String = item.title ?: ""
    val glideUrl: String? = resourceRepository.getGlideUrl(item.posterPath)

    val onClickListener = View.OnClickListener {
        Log.d("Navigator", "RowItemViewModel onClick")
        navigator.navigate(
            target = NavigationTarget(
                action = NavigationAction.DETAIL,
                itemId = item.id
            )
        )
    }
}