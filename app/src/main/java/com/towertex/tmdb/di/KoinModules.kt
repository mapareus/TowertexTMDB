package com.towertex.tmdb.di

import com.towertex.tmdb.MainActivity
import com.towertex.tmdb.fragments.BrowseFragment
import com.towertex.tmdb.fragments.DetailFragment
import com.towertex.tmdb.navigation.Navigator
import com.towertex.tmdb.navigation.NavigatorContract
import com.towertex.tmdb.repositories.ResourceRepository
import com.towertex.tmdb.repositories.ResourceRepositoryContract
import com.towertex.tmdb.viewModels.BrowseFragmentViewModel
import com.towertex.tmdb.viewModels.DetailFragmentViewModel
import com.towertex.tmdb.viewModels.MainActivityViewModel
import com.towertex.tmdb.viewModels.RowItemViewModel
import com.towertex.tmdbapi.TMDBApiBuilder
import com.towertex.tmdbmodel.TMDBModel
import com.towertex.tmdbmodel.model.RowItem
import com.towertex.tmdbmodel.room.TMDBDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTk1NDA5ZWU2NDdjOTVlNjMyZDNiMzJlYzc3ODBjZSIsInN1YiI6IjYzMmMzN2Y5YzhmM2M0MDA4M2VhYmM1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.dyUD9h6YUuIcRbx-pQY0HmCUc8xHd6qfBEcMDyFbd6I"

val repositoryModule = module {
    single { TMDBDatabase.buildDatabase(context = get()) }
    single { TMDBApiBuilder(BASE_URL, BEARER_TOKEN).build() }
    single { TMDBModel(api = get(), db = get()) }
    single { ResourceRepository(androidContext()) } bind ResourceRepositoryContract::class
    single { Navigator() } bind NavigatorContract::class
}

val viewModelModule = module {

    scope<MainActivity> {
        viewModel { MainActivityViewModel(navigator = get()) }
    }

    scope<BrowseFragment> {
        viewModel { BrowseFragmentViewModel(resourceRepository = get(), navigator = get(), model = get()) }
    }

    scope<DetailFragment> {
        viewModel { DetailFragmentViewModel(resourceRepository = get(), model = get()) }
    }

    factory { (item: RowItem) -> RowItemViewModel(resourceRepository = get(), navigator = get(), item = item) }
}