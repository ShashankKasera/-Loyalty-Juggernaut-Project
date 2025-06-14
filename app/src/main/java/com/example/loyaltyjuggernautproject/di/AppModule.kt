package com.example.loyaltyjuggernautproject.di

import android.content.Context
import androidx.room.Room
import com.example.loyaltyjuggernautproject.core.Constants
import com.example.loyaltyjuggernautproject.data.GHRepoRepository
import com.example.loyaltyjuggernautproject.data.GHRepoRepositoryImpl
import com.example.loyaltyjuggernautproject.data.local.Database
import com.example.loyaltyjuggernautproject.data.local.GHRepoDao
import com.example.loyaltyjuggernautproject.data.remote.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BASE_URL") baseUrl: String): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideDataSource(retrofit: Retrofit): DataSource = retrofit.create(DataSource::class.java)

    @Singleton
    @Provides
    fun provideGHRepoRepository(userRepositoryImpl: GHRepoRepositoryImpl): GHRepoRepository =
        userRepositoryImpl

    @Singleton
    @Provides
    @Named("ROOM_DB")
    fun provideDbName(): String = Constants.ROOM_DB

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext context: Context,
        @Named("ROOM_DB") name: String
    ): Database = Room.databaseBuilder(context, Database::class.java, name).build()

    @Singleton
    @Provides
    fun provideGHRepoDao(db: Database): GHRepoDao = db.getGHRepoDao()
}