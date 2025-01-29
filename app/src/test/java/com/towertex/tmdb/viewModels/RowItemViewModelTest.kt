package com.towertex.tmdb.viewModels

import android.util.Log
import com.towertex.tmdb.R
import com.towertex.tmdb.navigation.NavigationAction
import com.towertex.tmdb.navigation.NavigationTarget
import com.towertex.tmdb.navigation.NavigatorContract
import com.towertex.tmdb.repositories.ResourceRepositoryContract
import com.towertex.tmdbmodel.model.RowItem
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RowItemViewModelTest {

    private lateinit var resourceRepository: ResourceRepositoryContract
    private lateinit var navigator: NavigatorContract
    private lateinit var item: RowItem
    private lateinit var viewModel: RowItemViewModel

    companion object {
        private const val ITEM_MIN_HEIGHT = 100
        private const val DEFAULT_MARGIN = 16
        private const val WHITE = 0xFFFFFF
        private const val TITLE = "Test Title"
        private const val POSTER_PATH = "/image.jpg"
    }

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        resourceRepository = mockk(relaxed = true)
        navigator = mockk(relaxed = true)
        item = mockk(relaxed = true)
        every { resourceRepository.getDimensionPixelSize(R.dimen.item_min_height) } returns ITEM_MIN_HEIGHT
        every { resourceRepository.getDimensionPixelSize(R.dimen.default_margin) } returns DEFAULT_MARGIN
        every { resourceRepository.getColor(R.color.white) } returns WHITE
        every { item.title } returns TITLE
        every { resourceRepository.getGlideUrl(item.posterPath) } returns POSTER_PATH
        viewModel = RowItemViewModel(resourceRepository, navigator, item)
    }

    @Test
    fun `test values are set correctly`() {
        assertEquals(ITEM_MIN_HEIGHT, viewModel.minHeight)
        assertEquals(DEFAULT_MARGIN, viewModel.spacing)
        assertEquals(WHITE, viewModel.textColor)
        assertEquals(TITLE, viewModel.title)
        assertEquals(POSTER_PATH, viewModel.glideUrl)
    }

    @Test
    fun `test onClickListener navigates correctly`() {
        val onClickListener = viewModel.onClickListener
        onClickListener.onClick(mockk())

        verify {
            navigator.navigate(
                target = NavigationTarget(
                    action = NavigationAction.DETAIL,
                    itemId = item.id
                )
            )
        }
    }
}