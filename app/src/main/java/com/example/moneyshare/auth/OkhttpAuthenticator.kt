package com.example.moneyshare.auth

import com.example.moneyshare.repository.UserRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class OkhttpAuthenticator @Inject constructor(
    private val userRepository: UserRepository,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // Check if user is logged in
        if (!Auth.isLoggedIn()) return null
        // Get refresh token
        val refreshToken = Auth.getRefreshToken() ?: return null
        val accessTokenResult: Result<String>
        runBlocking {
            // Request a new access token with refresh token
            accessTokenResult = userRepository.refreshAccessToken(refreshToken)
        }
        if (accessTokenResult.isSuccess) {
            val accessToken = accessTokenResult.getOrNull() ?: return null
            Auth.saveAccessToken(accessToken)
            return response.request().newBuilder()
                .header("Authorization", accessToken)
                .build()
        } else {
            accessTokenResult.exceptionOrNull()?.printStackTrace()
            return null
        }
    }
}