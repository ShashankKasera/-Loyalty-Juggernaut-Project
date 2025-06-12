package com.example.loyaltyjuggernautproject.mappers


import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepo
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepoMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GHRepoMapperTest {

    private lateinit var mapper: GHRepoMapper

    @Before
    fun setUp() {
        mapper = GHRepoMapper()
    }

    @Test
    fun `map should convert GHRepoEntity to GHRepo correctly`() {
        // Given
        val entity = GHRepoEntity(
            id = 1234L, name = "Sample Repo", html_url = "https://github.com/sample/repo"
        )

        // When
        val result = mapper.map(entity)

        // Then
        val expected = GHRepo(
            id = 1234L, name = "Sample Repo", repoURL = "https://github.com/sample/repo"
        )

        assertEquals(expected, result)
    }
}
