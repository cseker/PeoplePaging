package com.cihadseker.main.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cihadseker.core.base.BaseViewModel
import com.cihadseker.main.data.domain.PeopleListUseCase
import com.cihadseker.main.data.paging.PeopleListPagingSource
import com.cihadseker.main.data.uimodel.PeopleUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FRHomeVM @Inject constructor(
    private val useCase: PeopleListUseCase
) : BaseViewModel() {

    fun fetchData(): Flow<PagingData<PeopleUI>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 300),
            pagingSourceFactory = { PeopleListPagingSource(useCase) }).flow.cachedIn(viewModelScope)
    }
}