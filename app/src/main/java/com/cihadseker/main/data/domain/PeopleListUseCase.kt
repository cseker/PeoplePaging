package com.cihadseker.main.data.domain

import com.cihadseker.core.base.BaseUseCase
import com.cihadseker.core.util.Mapper
import com.cihadseker.core.util.Tuple2
import com.cihadseker.main.data.repository.PeopleRepository
import com.cihadseker.main.data.repository.Person
import com.cihadseker.main.data.uimodel.PeopleUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PeopleListUseCase @Inject constructor(
    private val repo: PeopleRepository
) : BaseUseCase<PeopleListUseCase.Params, Tuple2<List<PeopleUI>, String>?>() {

    data class Params(val next: String?)

    override fun execute(params: Params): Flow<Tuple2<List<PeopleUI>, String>?> =
        repo.fetchData(params.next).map { itResponse ->
            itResponse?.let {
                Tuple2(response2UI(it.people), it.next ?: "")
            }
        }

    private fun response2UI(response: List<Person>): List<PeopleUI> {
        return object : Mapper<Person, PeopleUI>() {
            override fun map(value: Person): PeopleUI {
                return PeopleUI(
                    id = value.id,
                    name = value.fullName
                )
            }
        }.map(response)
    }
}