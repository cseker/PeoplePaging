package com.cihadseker.main.data.domain

import app.cash.turbine.test
import com.cihadseker.core.util.Tuple2
import com.cihadseker.main.data.repository.FetchResponse
import com.cihadseker.main.data.repository.PeopleRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

class PeopleListUseCaseTest {

    private lateinit var useCase: PeopleListUseCase

    @MockK
    private lateinit var repo: PeopleRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = PeopleListUseCase(repo)
    }

    @ExperimentalTime
    @Test
    fun `peopleListUseCase return success model`() = runBlocking {
        coEvery { repo.fetchData(any()) } coAnswers { flowOf(FetchResponse(listOf(), "next")) }

        useCase(PeopleListUseCase.Params("asd")).test {
            val item =awaitItem()
            Truth.assertThat(item).isInstanceOf(Tuple2::class.java)
            Truth.assertThat(item?.t2).isEqualTo("next")

            awaitComplete()
        }
    }
}