package com.example.moneyshare.di

import android.content.Context
import com.example.moneyshare.MainApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.Instant
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    @Singleton
    @Provides
    fun provideGsonConverter() : Gson {
        // Add converter for Instant type
        val gson = GsonBuilder()
            .registerTypeAdapter(Instant::class.java,
            JsonSerializer<Instant?>{ src, _, context ->
                context.serialize(src.epochSecond)
            })
            .registerTypeAdapter(Instant::class.java,
            JsonDeserializer<Any?> { json, _, _ ->
                val instant: Instant = Instant.ofEpochSecond(json.asJsonPrimitive.asLong)
                instant
            }).create()
        return gson
    }
}