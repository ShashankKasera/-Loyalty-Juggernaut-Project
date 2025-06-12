package com.example.loyaltyjuggernautproject.data

import com.example.loyaltyjuggernautproject.core.Dispatcher
import com.example.loyaltyjuggernautproject.core.LoyaltyJuggernautDispatchers
import com.example.loyaltyjuggernautproject.data.local.GHRepoDao
import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity
import com.example.loyaltyjuggernautproject.data.local.mapper.GHRepoEntityListMapper
import com.example.loyaltyjuggernautproject.data.remote.DataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class GHRepoRepositoryImpl @Inject constructor(
    @Dispatcher(LoyaltyJuggernautDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSource: DataSource,
    private val ghRepoDao: GHRepoDao,
    private val ghRepoEntityListMapper: GHRepoEntityListMapper
) : GHRepoRepository {
    override suspend fun getGHRepo(): Flow<Response<List<GHRepoEntity>>> {
        return flow {
            // Step 1: Load local DB first
            val cachedData = ghRepoDao.loadAllGHRepo()

            // Step 2: Emit local data (if any)
            if (cachedData.isNotEmpty()) {
                emit(Response.success(cachedData))
            }

            try {
                // Step 3: Try fetching from network
                val remoteData = dataSource.getGHRepo().items
                val mappedData = ghRepoEntityListMapper.map(remoteData)

                // Step 4: Save to DB
                ghRepoDao.insertAllGHRepo(mappedData)

                // Step 5: Load updated data and emit
                val updatedData = ghRepoDao.loadAllGHRepo()
                emit(Response.success(updatedData))

            } catch (e: IOException) {
                // Step 6: No internet or network failure → fallback to cached (already emitted)
                if (cachedData.isEmpty()) {
                    throw IOException("No internet and no cached data")
                }
            }
        }.flowOn(coroutineDispatcher)
    }
}
