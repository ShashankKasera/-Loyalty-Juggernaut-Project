package com.example.loyaltyjuggernautproject.mappers

import com.example.loyaltyjuggernautproject.data.local.mapper.GHRepoEntityListMapper
import com.example.loyaltyjuggernautproject.data.local.mapper.GHRepoEntityMapper
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GHRepoEntityListMapperTest {

    private lateinit var entityMapper: GHRepoEntityMapper
    private lateinit var listMapper: GHRepoEntityListMapper

    @Before
    fun setup() {
        entityMapper = GHRepoEntityMapper()
        listMapper = GHRepoEntityListMapper(entityMapper)
    }

    @Test
    fun `GHRepoEntityListMapper maps list correctly`() {
        // Given
        val responseList = listOf(
            GHRepoResponse(1L, "Repo1", "url1"), GHRepoResponse(2L, "Repo2", "url2")
        )

        // When
        val entityList = listMapper.map(responseList)

        // Then
        assertEquals(2, entityList.size)
        assertEquals("Repo1", entityList[0].name)
        assertEquals("url2", entityList[1].html_url)
    }
}