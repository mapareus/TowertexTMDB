package com.towertex.tmdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.towertex.tmdb.databinding.RowItemLoadingBinding

//This adapter handles errors states, loading animation and retry on the RecyclerView
class RowStateAdapter(
    private val retryBlock: () -> Unit
) : LoadStateAdapter<RowStateAdapter.RowLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: RowLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = RowLoadStateViewHolder(
        RowItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retryBlock
    )

    inner class RowLoadStateViewHolder(
        private val binding: RowItemLoadingBinding,
        private val retryBlock: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.buttonRetry.visibility = View.GONE
                    binding.textViewError.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.textViewError.text = loadState.error.localizedMessage
                    binding.progressbar.visibility = View.GONE
                    binding.buttonRetry.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.VISIBLE
                    binding.buttonRetry.setOnClickListener { retryBlock() }
                }
                else -> {
                    binding.progressbar.visibility = View.GONE
                    binding.buttonRetry.visibility = View.GONE
                    binding.textViewError.visibility = View.GONE
                }
            }
        }
    }
}