package com.example.loyaltyjuggernautproject.mappers


import com.example.loyaltyjuggernautproject.data.local.mapper.GHRepoEntityListMapper
import com.example.loyaltyjuggernautproject.data.local.mapper.GHRepoEntityMapper
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GHRepoEntityMapperTest {

    private lateinit var entityMapper: GHRepoEntityMapper
    private lateinit var listMapper: GHRepoEntityListMapper

    @Before
    fun setup() {
        entityMapper = GHRepoEntityMapper()
        listMapper = GHRepoEntityListMapper(entityMapper)
    }

    @Test
    fun `GHRepoEntityMapper maps correctly`() {
        // Given
        val response = GHRepoResponse(
            id = 1L, name = "TestRepo", html_url = "https://github.com/test/repo"
        )

        // When
        val entity = entityMapper.map(response)

        // Then
        assertEquals(1L, entity.id)
        assertEquals("TestRepo", entity.name)
        assertEquals("https://github.com/test/repo", entity.html_url)
    }
}
