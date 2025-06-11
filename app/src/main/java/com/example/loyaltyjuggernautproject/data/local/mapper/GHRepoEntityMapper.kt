package com.example.loyaltyjuggernautproject.data.local.mapper

import com.example.loyaltyjuggernautproject.core.Mapper
import com.example.loyaltyjuggernautproject.core.NullableInputListMapper
import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoResponse
import javax.inject.Inject

class GHRepoEntityMapper @Inject constructor() : Mapper<GHRepoResponse, GHRepoEntity> {
    override fun map(input: GHRepoResponse): GHRepoEntity {
        return GHRepoEntity(
            id = input.id, name = input.name, html_url = input.html_url
        )
    }
}

class GHRepoEntityListMapper @Inject constructor(
    private val ghRepoMapper: GHRepoEntityMapper
) : NullableInputListMapper<GHRepoResponse, GHRepoEntity> {
    override fun map(input: List<GHRepoResponse>?): List<GHRepoEntity> {
        return input?.map { ghRepoMapper.map(it) }.orEmpty()
    }
}