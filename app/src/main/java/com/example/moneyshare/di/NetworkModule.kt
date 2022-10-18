package com.example.moneyshare.di

import com.example.moneyshare.auth.Auth
import com.example.moneyshare.auth.OkhttpAuthenticator
import com.example.moneyshare.constant.Constant
import com.example.moneyshare.network.service.GroupService
import com.example.moneyshare.network.service.UserService
import com.example.moneyshare.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideUserService(gson: Gson): UserService {
        return Retrofit.Builder()
            .baseUrl("${Constant.IP}:${Constant.PORT}/user/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideGroupService(gson: Gson): GroupService {
        return Retrofit.Builder()
            .baseUrl("${Constant.IP}:${Constant.PORT}/group/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GroupService::class.java)
    }
}