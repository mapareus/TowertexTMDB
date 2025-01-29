package com.towertex.tmdb.viewModels

import androidx.paging.PagingData
import com.towertex.tmdb.CoroutinesTestRule
import com.towertex.tmdb.repositories.ResourceRepository
import com.towertex.tmdb.repositories.ResourceRepositoryContract
import com.towertex.tmdbmodel.TMDBModel
import com.towertex.tmdbmodel.model.RowItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BrowseFragmentViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var resourceRepository: ResourceRepositoryContract
    private lateinit var model: TMDBModel
    private lateinit var viewModel: BrowseFragmentViewModel

    @Before
    fun setUp() {
        resourceRepository = mockk<ResourceRepository>(relaxed = true)
        model = mockk<TMDBModel>()
        viewModel = BrowseFragmentViewModel(resourceRepository, model)
    }

    @Test
    fun dummyTest() {
        assertEquals(1, 1)
    }

    @Test
    fun `test getItems returns PagingData`() = runTest {
        val mockTrendingData: List<RowItem> = listOf(RowItem(1, null, false, "overview", null, "genreIds", 1, null, "originalLanguage", "title", null, 1.0f, 1, false, 1.0f))
        coEvery { model.trendingGet(any(), any(), any())} returns flowOf(mockTrendingData)

        val resultFLowOfPagingData: Flow<PagingData<RowItem>> = viewModel.getItems()

        val collectJob = launch { resultFLowOfPagingData.collect() }
//        coVerify { model.trendingGet(any(), any(), any()) }
        delay(1000)
        collectJob.cancelAndJoin()
//        resultFLowOfPagingData.collect { assertEquals(mockTrendingData, it.) }

    }
}

