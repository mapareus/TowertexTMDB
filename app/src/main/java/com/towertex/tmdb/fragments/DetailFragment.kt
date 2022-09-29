package com.towertex.tmdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.towertex.tmdb.databinding.FragmentDetailBinding
import com.towertex.tmdb.viewModels.DetailFragmentViewModel
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailFragment() : ScopeFragment() {

    constructor(id: Int?) : this() {
        arguments = (arguments ?: Bundle()).also { it.putInt(ARG_ITEM_ID, id ?: -1) }
    }

    private var nullableBinding: FragmentDetailBinding? = null
    private val binding get() = nullableBinding ?: throw UninitializedPropertyAccessException("DetailFragment binding missing")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id: Int? = arguments?.getInt(ARG_ITEM_ID)
        val _viewModel = getViewModel<DetailFragmentViewModel>()
        if (id != null && id != -1) _viewModel.setItemId(id)

        nullableBinding = FragmentDetailBinding
            .inflate(inflater, container, false)
            .apply {
                viewModel = _viewModel
                lifecycleOwner = this@DetailFragment.viewLifecycleOwner
            }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nullableBinding = null
    }

    companion object {
        const val ARG_ITEM_ID = "ARG_ITEM_ID"
    }
}