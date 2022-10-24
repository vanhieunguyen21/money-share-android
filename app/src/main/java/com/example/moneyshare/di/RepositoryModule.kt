package com.example.moneyshare.di

import com.example.moneyshare.network.service.ExpenseService
import com.example.moneyshare.network.service.GroupService
import com.example.moneyshare.room.AppDatabase
import com.example.moneyshare.network.service.UserService
import com.example.moneyshare.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideGroupRepository(groupService: GroupService): GroupRepository {
        return GroupRepositoryImpl(groupService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepositoryImpl(userService)
    }

    @Singleton
    @Provides
    fun provideExpenseRepository(expenseService: ExpenseService): ExpenseRepository {
        return ExpenseRepositoryImpl(expenseService)
    }
}