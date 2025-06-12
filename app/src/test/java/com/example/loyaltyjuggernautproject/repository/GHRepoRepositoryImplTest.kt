package com.example.loyaltyjuggernautproject.repository

import com.example.loyaltyjuggernautproject.data.GHRepoRepositoryImpl
import com.example.loyaltyjuggernautproject.data.local.GHRepoDao
import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import com.example.loyaltyjuggernautproject.data.local.mapper.GHRepoEntityListMapper
import com.example.loyaltyjuggernautproject.data.remote.DataSource
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoData
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GHRepoRepositoryImplTest {

    private lateinit var repository: GHRepoRepositoryImpl
    private val dataSource: DataSource = mockk()
    private val ghRepoDao: GHRepoDao = mockk(relaxed = true)
    private val mapper: GHRepoEntityListMapper = mockk()

    @Before
    fun setUp() {
        repository = GHRepoRepositoryImpl(
            coroutineDispatcher = Dispatchers.Unconfined,
            dataSource = dataSource,
            ghRepoDao = ghRepoDao,
            ghRepoEntityListMapper = mapper
        )
    }

    @Test
    fun `getGHRepo returns cached data and updates with remote data`() = runTest {
        // Given
        val cached = listOf(GHRepoEntity(1, "LocalRepo", "local_url"))
        val remoteItems = listOf(GHRepoResponse(2, "RemoteRepo", "remote_url"))
        val mapped = listOf(GHRepoEntity(2, "RemoteRepo", "remote_url"))
        val updated = cached + mapped

        coEvery { ghRepoDao.loadAllGHRepo() } returnsMany listOf(cached, updated)
        coEvery { dataSource.test() } returns GHRepoData(items = remoteItems)
        coEvery { mapper.map(remoteItems) } returns mapped
        coEvery { ghRepoDao.insertAllGHRepo(mapped) } returns Unit

        // When
        val result = repository.getGHRepo().toList()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0].isSuccessful)
        assertEquals("LocalRepo", result[0].body()?.first()?.name)
        assertEquals("RemoteRepo", result[1].body()?.last()?.name)
    }

    @Test
    fun `getGHRepo returns only cached data when no internet`() = runTest {
        // Given
        val cached = listOf(GHRepoEntity(1, "LocalRepo", "local_url"))
        coEvery { ghRepoDao.loadAllGHRepo() } returns cached
        coEvery { dataSource.test() } throws java.io.IOException("No internet")

        // When
        val result = repository.getGHRepo().first()

        // Then
        assertTrue(result.isSuccessful)
        assertEquals("LocalRepo", result.body()?.first()?.name)
    }

    @Test(expected = java.io.IOException::class)
    fun `getGHRepo throws when no cache and no internet`() = runTest {
        // Given
        coEvery { ghRepoDao.loadAllGHRepo() } returns emptyList()
        coEvery { dataSource.test() } throws java.io.IOException("No internet")

        // When
        repository.getGHRepo().first()
    }
}
