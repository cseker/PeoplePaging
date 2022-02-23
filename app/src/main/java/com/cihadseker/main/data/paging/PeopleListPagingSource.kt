package com.cihadseker.main.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cihadseker.main.data.domain.PeopleListUseCase
import com.cihadseker.main.data.uimodel.PeopleUI
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PeopleListPagingSource @Inject constructor(
    private val useCase: PeopleListUseCase
) : PagingSource<String, PeopleUI>() {

    override val keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<String, PeopleUI>): String {
        return state.anchorPosition.toString()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(params: LoadParams<String>): LoadResult<String, PeopleUI> =
        suspendCoroutine { cont ->
            GlobalScope.launch(Dispatchers.Main) {
                useCase(PeopleListUseCase.Params(params.key))
                    .catch { t ->
                        cont.resume(
                            LoadResult.Error(Exception(t.message))
                        )
                    }.collect { response ->
                        response?.let {
                            cont.resume(
                                LoadResult.Page(
                                    data = it.t1,
                                    prevKey = null,
                                    nextKey = it.t2
                                )
                            )
                        }
                    }
            }
        }
}