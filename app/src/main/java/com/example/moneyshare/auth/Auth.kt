package com.example.moneyshare.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.moneyshare.MainApplication
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.domain.model.User
import com.example.moneyshare.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Singleton

object Auth {
    private val preferenceName: String = "auth"
    private val accessTokenKey = "access_token"
    private val refreshTokenKey = "refresh_token"
    private val userKey = "user"

    lateinit var app: MainApplication
    lateinit var gson: Gson

    val loggedInUser: MutableState<User?> = mutableStateOf(null)

    private val sharedPref: SharedPreferences by lazy {
        app.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
    }

    fun init(app: MainApplication, gson: Gson) {
        this.app = app
        this.gson = gson
        loadSavedUser()
    }

    fun isLoggedIn(): Boolean {
        return loggedInUser.value != null
    }

    fun saveAccessToken(token: String): Auth {
        sharedPref.edit().putString(accessTokenKey, token).apply()
        return this
    }

    fun getAccessToken(): String? {
        return sharedPref.getString(accessTokenKey, null)
    }

    fun saveRefreshToken(token: String): Auth {
        sharedPref.edit().putString(refreshTokenKey, token).apply()
        return this
    }

    fun getRefreshToken(): String? {
        return sharedPref.getString(refreshTokenKey, null)
    }

    fun saveUser(user: User): Auth {
        val userJson = gson.toJson(user)
        sharedPref.edit().putString(userKey, userJson).apply()
        loggedInUser.value = user
        return this
    }

    private fun loadSavedUser() {
        val userJson = sharedPref.getString(userKey, null) ?: return
        val user = gson.fromJson(userJson, User::class.java)
        loggedInUser.value = user
    }

    fun logOut() {
        sharedPref.edit().clear().apply()
        loggedInUser.value = null
    }
}