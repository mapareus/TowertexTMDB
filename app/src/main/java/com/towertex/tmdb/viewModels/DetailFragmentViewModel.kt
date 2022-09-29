package com.towertex.tmdb.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towertex.tmdb.R
import com.towertex.tmdb.repositories.ResourceRepositoryContract
import com.towertex.tmdbmodel.TMDBModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailFragmentViewModel(
    resourceRepository: ResourceRepositoryContract,
    model: TMDBModel
): ViewModel() {

    val spacing: Int = resourceRepository.getDimensionPixelSize(R.dimen.default_margin)
    val textColor: Int = resourceRepository.getColor(R.color.black)

    private val itemIdSF = MutableStateFlow<Int?>(null)
    private val _glideUrl = MutableStateFlow<String?>(null)
    private val _title = MutableStateFlow<String>("")

    val glideUrl: StateFlow<String?> = _glideUrl
    val title: StateFlow<String> = _title

    init {
        viewModelScope.launch {
            itemIdSF.flatMapLatest { model.trendingGet(it) }.collect {
                _glideUrl.value = it?.let { resourceRepository.getGlideUrl(it.posterPath) }
                //TODO the string building needs to go through resources
                _title.value = "Title: ${it?.title}\n\n" +
                        "Overview: ${it?.overview}\n\n" +
                        "Release date: ${it?.releaseDate}\n\n" +
                        "Popularity: ${it?.popularity}\n\n" +
                        "Vote count: ${it?.voteCount}\n\n" +
                        "Vote average: ${it?.voteAverage}"
            }
        }
    }

    fun setItemId(id: Int) {
        itemIdSF.value = id
    }
}