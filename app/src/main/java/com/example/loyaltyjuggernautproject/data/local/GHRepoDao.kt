package com.example.loyaltyjuggernautproject.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity

@Dao
interface GHRepoDao {

    @Insert
    fun insertGHRepo(ghRepo: GHRepoEntity)

    @Update
    suspend fun upDateGHRepo(ghRepo: GHRepoEntity)

    @Delete
    suspend fun deleteGHRepo(ghRepo: GHRepoEntity)

    @Insert
    fun insertAllGHRepo(vararg ghRepo: GHRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGHRepo(users: List<GHRepoEntity>)

    @Query("Select * from GHRepoEntity")
    fun loadAllGHRepo(): List<GHRepoEntity>
}