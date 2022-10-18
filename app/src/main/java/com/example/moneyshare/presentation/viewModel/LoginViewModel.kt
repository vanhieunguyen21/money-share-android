package com.example.moneyshare.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var usernameError by mutableStateOf(false)
    var usernameErrorMessage by mutableStateOf("")
    var usernameValidated by mutableStateOf(false)
    private var usernameCheckJob: Job? = null

    var passwordError by mutableStateOf(false)
    var passwordErrorMessage by mutableStateOf("")
    var passwordValidated by mutableStateOf(false)
    private var passwordCheckJob: Job? = null

    var loginStatus = MutableSharedFlow<JobStatus>()
    var loginErrorMessage by mutableStateOf("")

    fun onUsernameChange(value: String) {
        username = value

        // Reset check fields
        usernameError = false
        usernameErrorMessage = ""
        usernameValidated = false

        // Cancel current check job
        usernameCheckJob?.cancel()

        if (value.isEmpty()) return

        // Start a new check job
        usernameCheckJob = viewModelScope.launch {
            delay(500)

            if (isActive) {
                if (value.length < 8) {
                    usernameError = true
                    usernameErrorMessage = "Username must have at least 8 characters"
                    usernameValidated = false
                } else {
                    usernameError = false
                    usernameErrorMessage = ""
                    usernameValidated = true
                }
            }
        }
    }

    fun onPasswordChange(value: String) {
        password = value

        // Reset check fields
        passwordError = false
        passwordErrorMessage = ""
        passwordValidated = false

        // Cancel current check job
        passwordCheckJob?.cancel()

        if (value.isEmpty()) return

        // Start a new check job
        passwordCheckJob = viewModelScope.launch {
            delay(500)

            if (isActive) {
                if (value.length < 8) {
                    passwordError = true
                    passwordErrorMessage = "Password must have at least 8 characters"
                    passwordValidated = false
                } else {
                    passwordError = false
                    passwordErrorMessage = ""
                    passwordValidated = true
                }
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            loginStatus.emit(JobStatus.Processing)
            val result = userRepository.login(username, password)
            if (result.isSuccess) {
                val loginResponse = result.getOrNull()
                if (loginResponse != null) {
                    try {
                        val accessToken = loginResponse.accessToken
                        val refreshToken = loginResponse.refreshToken
                        val user = loginResponse.getUser()
                        // Save authentication information
                        Auth.saveAccessToken(accessToken)
                            .saveRefreshToken(refreshToken)
                            .saveUser(user)

                        loginStatus.emit(JobStatus.Success)
                    } catch (e: Exception) {
                        loginErrorMessage = e.message.orEmpty()
                        loginStatus.emit(JobStatus.Failure)
                    }
                } else {
                    loginErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                    loginStatus.emit(JobStatus.Failure)
                }
            } else {
                loginErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                loginStatus.emit(JobStatus.Failure)
            }
        }
    }
}