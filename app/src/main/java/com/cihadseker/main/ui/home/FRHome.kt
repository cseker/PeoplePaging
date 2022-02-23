package com.cihadseker.main.ui.home

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.cihadseker.core.base.BaseFragment
import com.cihadseker.core.extension.injectVM
import com.cihadseker.main.R
import com.cihadseker.main.databinding.FragmentHomeBinding
import com.cihadseker.main.ui.home.adapter.AdapterPeople
import com.cihadseker.main.ui.home.adapter.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class FRHome : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: FRHomeVM by injectVM(brId = com.cihadseker.main.BR.viewModel)

    @Inject
    lateinit var adapterPeople: AdapterPeople

    override fun getLayoutId() = R.layout.fragment_home

    override fun initViews() {
        with(vi) {
            with(adapterPeople) {
                srSwipeRefresh.setOnRefreshListener { refresh() }
                rvPeopleList.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )
            }
        }
    }

    override fun setListener() {
        adapterPeople.apply {
            itemClickListener = {
                showToast("Isim: ${it.name}")
            }
        }
    }

    override fun setReceiver() {
        with(adapterPeople) {
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.fetchData().collectLatest {
                    submitData(it)
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                loadStateFlow.collectLatest {
                    vi.srSwipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                loadStateFlow.distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { vi.rvPeopleList.scrollToPosition(0) }
            }
        }
    }
}