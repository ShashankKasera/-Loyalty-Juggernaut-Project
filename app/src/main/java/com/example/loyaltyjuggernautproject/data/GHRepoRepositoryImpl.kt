package com.example.loyaltyjuggernautproject.data

import com.example.loyaltyjuggernautproject.core.Dispatcher
import com.example.loyaltyjuggernautproject.core.LoyaltyJuggernautDispatchers
import com.example.loyaltyjuggernautproject.data.remote.DataSource
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.GHRepoData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class GHRepoRepositoryImpl @Inject constructor(
    @Dispatcher(LoyaltyJuggernautDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSource: DataSource
) : GHRepoRepository {
    override suspend fun getGHRepo(): Flow<Response<GHRepoData>> {
        return flow {
            emit(dataSource.getGHRepo())
        }.flowOn(coroutineDispatcher)
    }
}
