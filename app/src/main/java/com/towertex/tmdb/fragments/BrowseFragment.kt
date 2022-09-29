package com.towertex.tmdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.towertex.tmdb.R
import com.towertex.tmdb.adapters.RowAdapter
import com.towertex.tmdb.adapters.RowStateAdapter
import com.towertex.tmdb.databinding.FragmentBrowseBinding
import com.towertex.tmdb.viewModels.BrowseFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

class BrowseFragment : ScopeFragment() {

    private var nullableBinding: FragmentBrowseBinding? = null
    private val binding get() = nullableBinding ?: throw UninitializedPropertyAccessException("BrowseFragment binding missing")
    private val rowAdapter = RowAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentBrowseBinding
            .inflate(inflater, container, false)
            .apply {
                viewModel = getViewModel<BrowseFragmentViewModel>()
                lifecycleOwner = this@BrowseFragment.viewLifecycleOwner
            }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nullableBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
    }

    private fun setupRecycler() {
        val aLayoutManager = LinearLayoutManager(requireContext())
        val itemDividerDecoration = DividerItemDecoration(context, aLayoutManager.orientation).apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.also { setDrawable(it) }
        }

        with(binding.row) {
            adapter = rowAdapter.withLoadStateHeaderAndFooter(
                header = RowStateAdapter { rowAdapter.retry() },
                footer = RowStateAdapter { rowAdapter.retry() }
            )
            layoutManager = aLayoutManager
            setHasFixedSize(true)
            addItemDecoration(itemDividerDecoration)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.viewModel?.getItems()?.collectLatest {
                    rowAdapter.submitData(it)
                }
            }
        }
    }
}