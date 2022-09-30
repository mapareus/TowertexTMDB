package com.towertex.tmdb

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.towertex.tmdb.databinding.ActivityMainBinding
import com.towertex.tmdb.navigation.*
import com.towertex.tmdb.viewModels.MainActivityViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ScopeActivity(), ActivityContract {

    private lateinit var binding: ActivityMainBinding
    internal val navigator: NavigatorContract by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            viewModel = getViewModel<MainActivityViewModel>()
        }
        setContentView(binding.root)
        binding.viewModel?.onCreate()
    }

    override fun onPause() {
        navigator.setActivityContract(null)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        navigator.setActivityContract(this)
    }

    override fun navigate(fragmentFactory: () -> Fragment, location: NavigationLocation, tag: String?) {
        internalNavigate(fragmentFactory, location, tag)
    }

    override fun onBackPressed() {
        internalOnBackPressed()
    }
}