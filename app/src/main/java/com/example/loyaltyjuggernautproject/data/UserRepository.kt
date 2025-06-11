package com.example.loyaltyjuggernautproject.data

import com.example.loyaltyjuggernautproject.data.remote.networkmodel.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
    suspend fun getUser(): Flow<Response<UserResponse>>
}