package com.example.loyaltyjuggernautproject.viewmodel

import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.data.GHRepoRepository
import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepo
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepoListMapper
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepoViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response


@OptIn(ExperimentalCoroutinesApi::class)
class GHRepoViewModelTest {

    private lateinit var viewModel: GHRepoViewModel
    private lateinit var repository: GHRepoRepository
    private lateinit var mapper: GHRepoListMapper
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        mapper = mockk()
        viewModel = GHRepoViewModel(repository, mapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUser() emits Success state and updates repos`() = runTest {
        // Given
        val fakeEntityList = listOf(GHRepoEntity(1L, "Name1", "url1"))
        val mappedList = listOf(GHRepo(1L, "Name1", "url1"))

        coEvery { repository.getGHRepo() } returns flowOf(Response.success(fakeEntityList))
        every { mapper.map(fakeEntityList) } returns mappedList

        // When
        viewModel.getUser()
        advanceUntilIdle()

        // Then
        assertEquals(ApiState.Success, viewModel.apiState.value)
        assertEquals(mappedList, viewModel.searchedGhRepo.value)
    }

    @Test
    fun `getUser() emits Error state on failure`() = runTest {
        // Given
        val exception = RuntimeException("API failed")
        coEvery { repository.getGHRepo() } returns flow { throw exception }

        // When
        viewModel.getUser()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.apiState.value is ApiState.Error)
        assertEquals("API failed", (viewModel.apiState.value as ApiState.Error).msg)
    }

    @Test
    fun `search filters GHRepo list based on query`() = runTest {
        // Given - mock repository response
        val fakeEntityList = listOf(
            GHRepoEntity(1, "KotlinRepo", "url1"),
            GHRepoEntity(2, "JavaRepo", "url2"),
            GHRepoEntity(3, "ComposeLib", "url3")
        )
        val mappedList = listOf(
            GHRepo(1, "KotlinRepo", "url1"),
            GHRepo(2, "JavaRepo", "url2"),
            GHRepo(3, "ComposeLib", "url3")
        )

        coEvery { repository.getGHRepo() } returns flowOf(Response.success(fakeEntityList))
        every { mapper.map(fakeEntityList) } returns mappedList

        // When - call getUser() to populate repo list
        viewModel.getUser()
        advanceUntilIdle()

        // And - perform search
        viewModel.search("java")
        advanceTimeBy(400) // for debounce to pass

        // Then - assert filtered result
        val result = viewModel.searchedGhRepo.value
        assertEquals(1, result.size)
        assertEquals("JavaRepo", result.first().name)
    }
}