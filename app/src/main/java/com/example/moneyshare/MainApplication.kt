package com.example.moneyshare

import android.app.Application
import android.content.Context
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.Group
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var gson: Gson

    // Shared flow for components which listen to new group events
    val newGroupFlow = MutableSharedFlow<Group>()

    override fun onCreate() {
        super.onCreate()

        Auth.init(this, gson)
    }
}