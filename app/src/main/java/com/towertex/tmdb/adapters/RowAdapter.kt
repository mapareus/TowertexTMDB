package com.towertex.tmdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.towertex.tmdb.databinding.RowItemBinding
import com.towertex.tmdbmodel.model.RowItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class RowAdapter : PagingDataAdapter<RowItem, RowAdapter.RowItemViewHolder>(RowItemComparator), KoinComponent {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RowItemViewHolder {
        return RowItemViewHolder(
            RowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RowItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindMovieListResultObject(it) }
    }

    inner class RowItemViewHolder(
        private val binding: RowItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindMovieListResultObject(item: RowItem) = with(binding) {
            viewModel = get { parametersOf(item) } //Koin injection
        }
    }

    object RowItemComparator : DiffUtil.ItemCallback<RowItem>() {
        override fun areItemsTheSame(oldItem: RowItem, newItem: RowItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RowItem, newItem: RowItem): Boolean {
            return oldItem == newItem
        }
    }
}