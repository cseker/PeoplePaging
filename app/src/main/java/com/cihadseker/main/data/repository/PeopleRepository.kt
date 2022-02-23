package com.cihadseker.main.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface PeopleRepository {
    fun fetchData(next: String?): Flow<FetchResponse?>
}

class PeopleRepositoryImpl @Inject constructor(
    private val dataSource: DataSource
) : PeopleRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun fetchData(next: String?): Flow<FetchResponse?> = callbackFlow {

        val callback = object : FetchCompletionHandler {
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {
                p2?.let {
                    close(Exception(p2.errorDescription))
                } ?: trySend(p1)
            }
        }

        dataSource.fetch(next, completionHandler = callback)

        awaitClose {
            dataSource.fetch(next, completionHandler = callback)
        }
    }
}
