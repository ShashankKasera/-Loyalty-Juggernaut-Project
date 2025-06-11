package com.example.loyaltyjuggernautproject.data.remote

import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoData
import retrofit2.Response
import retrofit2.http.GET

interface DataSource {

    @GET("repositories?q=language:swift&sort=stars&order=desc")
    suspend fun getGHRepo(): Response<GHRepoData>
}