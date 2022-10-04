package com.example.moneyshare.presentation.ui.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")

    // State for username check
    val usernameError = mutableStateOf(false)
    var usernameErrorMessage: String = ""
    val usernameChecking = mutableStateOf(false)
    val usernameAvailable = mutableStateOf(false)
    private var usernameCheckJob: Job? = null

    fun onUsernameChange(value: String) {
        username.value = value

        // Cancel ongoing username check job
        usernameCheckJob?.cancel()

        // Start a new check job
        usernameCheckJob = viewModelScope.launch {
            // Reset username check fields
            usernameError.value = false
            usernameErrorMessage = ""
            usernameChecking.value = true
            usernameAvailable.value = false

            // Check username requirement
            if (value.length < 6) {
                usernameError.value = true
                usernameErrorMessage = "Length must be equal or greater than 6"
                usernameChecking.value = false
                return@launch
            }

            // Check username availability
            delay(1000)
            if (isActive) {
                val result = userRepository.checkUsernameAvailability(value)
                Log.d("Debug", "onUsernameChange: ${result.result}")
                if (isActive){
                    usernameChecking.value = false
                    if (result.result) {
                        usernameError.value = false
                        usernameAvailable.value = true
                    } else {
                        usernameError.value = true
                        usernameErrorMessage = result.message.orEmpty()
                        usernameAvailable.value = false
                    }
                }
            }
        }


    }

    fun onPasswordChange(value: String) {
        password.value = value
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword.value = value
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}