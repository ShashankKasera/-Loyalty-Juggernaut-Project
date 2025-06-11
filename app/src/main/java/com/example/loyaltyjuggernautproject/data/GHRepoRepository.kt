package com.example.loyaltyjuggernautproject.data

import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GHRepoRepository {
    suspend fun getGHRepo(): Flow<Response<List<GHRepoEntity>>>
}