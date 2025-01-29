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
            itemIdSF.flatMapLatest { model.trendingGet(it) }.collect { rowItem -> rowItem?.run {
                _glideUrl.value = resourceRepository.getGlideUrl(posterPath)
                _title.value = listOf(
                    title to R.string.title,
                    overview to R.string.overview,
                    releaseDate to R.string.release_date,
                    popularity to R.string.popularity,
                    voteCount to R.string.vote_count,
                    voteAverage to R.string.vote_average
                )
                    .filter { it.first?.toString()?.isNotEmpty() == true }
                    .joinToString("\n\n") { "${resourceRepository.getString(it.second)}: ${it.first}" }
            } }
        }
    }

    fun setItemId(id: Int) {
        itemIdSF.value = id
    }
}