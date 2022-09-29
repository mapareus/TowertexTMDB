package com.towertex.tmdb.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.towertex.tmdb.R
import com.towertex.tmdb.adapters.RowPagingSource
import com.towertex.tmdb.navigation.NavigatorContract
import com.towertex.tmdb.repositories.ResourceRepositoryContract
import com.towertex.tmdbmodel.TMDBModel
import com.towertex.tmdbmodel.model.RowItem
import kotlinx.coroutines.flow.Flow

class BrowseFragmentViewModel(
    val resourceRepository: ResourceRepositoryContract,
    val navigator: NavigatorContract,
    val model: TMDBModel
): ViewModel() {

    val spacing: Int = resourceRepository.getDimensionPixelSize(R.dimen.default_margin)

    fun getItems(): Flow<PagingData<RowItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { RowPagingSource(model = model) }
        ).flow.cachedIn(viewModelScope)
    }
}