package com.example.loyaltyjuggernautproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loyaltyjuggernautproject.data.local.entity.GHRepoEntity

@Database(
    entities = [
        GHRepoEntity::class,
    ], version = 1, exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun getGHRepoDao(): GHRepoDao
}