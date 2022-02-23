package com.cihadseker.main.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cihadseker.main.R
import com.cihadseker.main.databinding.ItemLoadingStateBinding

class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.LoadingStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadingStateItemViewHolder(
            ItemLoadingStateBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading_state, parent, false)
            )
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: LoadingStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class LoadingStateItemViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btRetry.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                pbProgress.isVisible = loadState is LoadState.Loading
                btRetry.isVisible = loadState is LoadState.Error
                tvErrorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                tvErrorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}