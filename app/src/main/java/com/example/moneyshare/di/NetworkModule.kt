package com.example.moneyshare.di

import com.example.moneyshare.constant.Constant
import com.example.moneyshare.network.service.UserService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideUserService(): UserService {
        return Retrofit.Builder()
            .baseUrl("${Constant.IP}:${Constant.PORT}/user/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(UserService::class.java)
    }
}