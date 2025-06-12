package com.example.loyaltyjuggernautproject.ui.ghrepolist

import com.example.loyaltyjuggernautproject.core.ListMapper
import com.example.loyaltyjuggernautproject.core.Mapper
import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import javax.inject.Inject

class GHRepoMapper @Inject constructor() : Mapper<GHRepoEntity, GHRepo> {
    override fun map(input: GHRepoEntity): GHRepo {
        return GHRepo(
            id = input.id, name = input.name, repoURL = input.html_url
        )
    }
}

class GHRepoListMapper @Inject constructor(
    private val ghRepoMapper: GHRepoMapper
) : ListMapper<GHRepoEntity, GHRepo> {

    override fun map(input: List<GHRepoEntity>): List<GHRepo> {
        return input.map { ghRepoMapper.map(it) }
    }
}