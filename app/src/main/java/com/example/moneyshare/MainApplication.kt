package com.example.moneyshare

import android.app.Application
import android.util.Log
import com.example.moneyshare.repository.GroupRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
//    @Inject
//    lateinit var groupRepository: GroupRepository

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
//            val group = groupRepository.createGroup("Hello")
//            Log.d("Debug", "onCreate: $group")
        }
    }
}