package com.example.loyaltyjuggernautproject.di

import android.content.Context
import androidx.room.Room
import com.example.loyaltyjuggernautproject.Constants
import com.example.loyaltyjuggernautproject.data.UserRepository
import com.example.loyaltyjuggernautproject.data.UserRepositoryImpl
import com.example.loyaltyjuggernautproject.data.local.Database
import com.example.loyaltyjuggernautproject.data.local.UserDao
import com.example.loyaltyjuggernautproject.data.remote.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providePostRequest(retrofit: Retrofit): DataSource = retrofit.create(DataSource::class.java)

    @Singleton
    @Provides
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Singleton
    @Provides
    fun provideDbName(): String = Constants.ROOM_DB

    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context, name: String): Database =
        Room.databaseBuilder(context, Database::class.java, name).build()

    @Singleton
    @Provides
    fun provideUserDao(db: Database): UserDao = db.getUserDao()

}