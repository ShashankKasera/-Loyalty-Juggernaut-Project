package com.example.loyaltyjuggernautproject.data

import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GHRepoRepository {
    suspend fun getGHRepo(): Flow<Response<GHRepoData>>
}