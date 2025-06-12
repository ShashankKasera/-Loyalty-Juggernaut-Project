package com.example.loyaltyjuggernautproject.mappers

import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepo
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepoListMapper
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepoMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GHRepoListMapperTest {

    private lateinit var ghRepoMapper: GHRepoMapper
    private lateinit var ghRepoListMapper: GHRepoListMapper

    @Before
    fun setup() {
        ghRepoMapper = GHRepoMapper()
        ghRepoListMapper = GHRepoListMapper(ghRepoMapper)
    }

    @Test
    fun `GHRepoListMapper maps list of GHRepoEntity to GHRepo correctly`() {
        // Given
        val entities = listOf(
            GHRepoEntity(id = 1, name = "RepoOne", html_url = "https://github.com/one"),
            GHRepoEntity(id = 2, name = "RepoTwo", html_url = "https://github.com/two")
        )

        // When
        val mappedRepos: List<GHRepo> = ghRepoListMapper.map(entities)

        // Then
        assertEquals(2, mappedRepos.size)
        assertEquals("RepoOne", mappedRepos[0].name)
        assertEquals("https://github.com/two", mappedRepos[1].repoURL)
    }
}
