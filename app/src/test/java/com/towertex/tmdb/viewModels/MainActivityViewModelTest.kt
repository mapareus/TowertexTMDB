package com.towertex.tmdb.viewModels

import android.util.Log
import com.towertex.tmdb.navigation.NavigationAction
import com.towertex.tmdb.navigation.NavigationTarget
import com.towertex.tmdb.navigation.NavigatorContract
import io.mockk.*
import org.junit.Before
import org.junit.Test

class MainActivityViewModelTest {

    private lateinit var navigator: NavigatorContract
    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        navigator = mockk(relaxed = true)
        viewModel = MainActivityViewModel(navigator)
    }

    @Test
    fun `test onCreate navigates to pending target if available`() {
        val pendingTarget = NavigationTarget(action = NavigationAction.DETAIL)
        every { navigator.pendingNavigationTarget } returns pendingTarget
        viewModel.onCreate()
        verify { navigator.navigate(target = pendingTarget) }
    }

    @Test
    fun `test onCreate navigates to BROWSE if no pending target`() {
        every { navigator.pendingNavigationTarget } returns null
        viewModel.onCreate()
        verify {
            navigator.navigate(
                target = NavigationTarget(action = NavigationAction.BROWSE)
            )
        }
    }
}