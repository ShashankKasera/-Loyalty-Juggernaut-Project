package com.example.loyaltyjuggernautproject.ui

import com.example.loyaltyjuggernautproject.core.Mapper
import com.example.loyaltyjuggernautproject.core.NullableInputListMapper
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoResponse
import javax.inject.Inject

class GHRepoMapper @Inject constructor() : Mapper<GHRepoResponse, GHRepo> {
    override fun map(input: GHRepoResponse): GHRepo {
        return GHRepo(
            id = input.id, name = input.name, repoURL = input.html_url
        )
    }
}

class GHRepoListMapper @Inject constructor(
    private val ghRepoMapper: GHRepoMapper
) : NullableInputListMapper<GHRepoResponse, GHRepo> {
    override fun map(input: List<GHRepoResponse>?): List<GHRepo> {
        return input?.map { ghRepoMapper.map(it) }.orEmpty()
    }
}