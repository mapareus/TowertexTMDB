package com.towertex.tmdb.viewModels

import com.towertex.tmdb.CoroutinesTestRule
import com.towertex.tmdb.R
import com.towertex.tmdb.repositories.ResourceRepositoryContract
import com.towertex.tmdbmodel.TMDBModel
import com.towertex.tmdbmodel.model.RowItem
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailFragmentViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var resourceRepository: ResourceRepositoryContract
    private lateinit var model: TMDBModel
    private lateinit var viewModel: DetailFragmentViewModel

    companion object {
        private const val DEFAULT_MARGIN = 16
        private const val BLACK = 0x000000
        private const val POSTER_PATH_1 = "/test1.jpg"
        private const val POSTER_PATH_2 = "/test2.jpg"
        private const val GLIDE_URL_1 = "https://image.tmdb.org/t/p/w300/test1.jpg"
        private const val GLIDE_URL_2 = "https://image.tmdb.org/t/p/w300/test2.jpg"
        private const val TITLE = "Test Title"
        private const val POPULARITY = 10.0f
        private const val VOTE_COUNT = 100
        private const val VOTE_AVERAGE = 8.5f
        private val mockRowItem1 = RowItem(
            id = 1,
            title = TITLE,
            overview = "Test Overview",
            releaseDate = "2022-01-01",
            popularity = POPULARITY,
            voteCount = VOTE_COUNT,
            voteAverage = VOTE_AVERAGE,
            posterPath = POSTER_PATH_1,
            page = 1,
            adult = false,
            genreIds = "",
            originalTitle = null,
            originalLanguage = "",
            backdropPath = null,
            video = false,
        )
        private val mockRowItem2 = RowItem(
            id = 2,
            title = TITLE,
            overview = "",
            releaseDate = null,
            popularity = POPULARITY,
            voteCount = VOTE_COUNT,
            voteAverage = VOTE_AVERAGE,
            posterPath = POSTER_PATH_2,
            page = 1,
            adult = false,
            genreIds = "",
            originalTitle = null,
            originalLanguage = "",
            backdropPath = null,
            video = false,
        )
    }

    @Before
    fun setUp() {
        resourceRepository = mockk(relaxed = true)
        model = mockk(relaxed = true)
        every { resourceRepository.getDimensionPixelSize(R.dimen.default_margin) } returns DEFAULT_MARGIN
        every { resourceRepository.getColor(R.color.black) } returns BLACK
        viewModel = DetailFragmentViewModel(resourceRepository, model)
    }

    @Test
    fun `test values are set correctly`() {
        assertEquals(DEFAULT_MARGIN, viewModel.spacing)
        assertEquals(BLACK, viewModel.textColor)
    }

    @Test
    fun `test glideUrl is updated correctly`() = runTest {
        every { model.trendingGet(1) } returns flowOf(mockRowItem1)
        every { model.trendingGet(2) } returns flowOf(mockRowItem2)
        every { resourceRepository.getGlideUrl(POSTER_PATH_1) } returns GLIDE_URL_1
        every { resourceRepository.getGlideUrl(POSTER_PATH_2) } returns GLIDE_URL_2
        viewModel.setItemId(1)
        testScheduler.advanceUntilIdle()
        assertEquals(GLIDE_URL_1, viewModel.glideUrl.value)
        viewModel.setItemId(2)
        testScheduler.advanceUntilIdle()
        assertEquals(GLIDE_URL_2, viewModel.glideUrl.value)
    }

    @Test
    fun `test title is updated correctly`() = runTest {
        every { model.trendingGet(2) } returns flowOf(mockRowItem2)
        every { resourceRepository.getString(any()) } returns TITLE
        viewModel.setItemId(2)
        testScheduler.advanceUntilIdle()
        assertEquals(
            "$TITLE: $TITLE\n\n$TITLE: $POPULARITY\n\n$TITLE: $VOTE_COUNT\n\n$TITLE: $VOTE_AVERAGE",
            viewModel.title.value
        )
    }
}