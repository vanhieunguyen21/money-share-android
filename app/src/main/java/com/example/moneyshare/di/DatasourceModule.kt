package com.example.moneyshare.di

import android.content.Context
import androidx.room.Room
import com.example.moneyshare.domain.data_source.room.AppDatabase
import com.example.moneyshare.domain.data_source.room.dao.GroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app_database").build()
    }

    @Singleton
    @Provides
    fun provideGroupDao(appDatabase: AppDatabase): GroupDao {
        return appDatabase.groupDao()
    }
}