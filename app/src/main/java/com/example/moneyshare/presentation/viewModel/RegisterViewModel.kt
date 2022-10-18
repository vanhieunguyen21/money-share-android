package com.example.moneyshare.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var username by mutableStateOf("")
    var displayName by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    // State for username check
    var usernameValidated by mutableStateOf(false)
    var usernameError by mutableStateOf(false)
    var usernameErrorMessage by mutableStateOf("")
    var usernameChecking by mutableStateOf(false)
    var usernameAvailable by mutableStateOf(false)
    private var usernameCheckJob: Job? = null

    // State for displayName check
    var displayNameValidated by mutableStateOf(false)
    var displayNameError by mutableStateOf(false)
    var displayNameErrorMessage by mutableStateOf("")
    private var displayNameCheckJob: Job? = null

    // State for password check
    var passwordValidated by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var passwordErrorMessage by mutableStateOf("")
    private var passwordCheckJob: Job? = null

    // State for confirm password check
    var confirmPasswordValidated by mutableStateOf(false)
    var confirmPasswordError by mutableStateOf(false)
    var confirmPasswordErrorMessage by mutableStateOf("")
    private var confirmPasswordCheckJob: Job? = null

    // State for register
    val registerStatus = MutableSharedFlow<JobStatus>()
    var registerErrorMessage by mutableStateOf("")

    fun onUsernameChange(value: String) {
        username = value

        // Cancel ongoing username check job
        usernameCheckJob?.cancel()

        // Reset username check fields
        usernameError = false
        usernameErrorMessage = ""
        usernameAvailable = false
        usernameValidated = false
        usernameChecking = false

        if (value.isEmpty()) return

        usernameChecking = true

        // Start a new check job
        usernameCheckJob = viewModelScope.launch {
            delay(500)
            // Check username requirement
            if (value.length < 8) {
                usernameError = true
                usernameErrorMessage = "Username must be at least 8 characters"
                usernameChecking = false
                usernameValidated = false
                return@launch
            } else if (value.length > 20) {
                usernameError = true
                usernameErrorMessage = "Username must be at most 20 characters"
                usernameChecking = false
                usernameValidated = false
                return@launch
            }

            // Check username availability
            if (isActive) {
                val result = userRepository.checkUsernameAvailability(value)
                if (isActive) {
                    usernameChecking = false
                    if (result.isSuccess) {
                        if (result.getOrDefault(false)) {
                            usernameError = false
                            usernameAvailable = true
                            usernameValidated = true
                        } else {
                            usernameError = true
                            usernameErrorMessage = "Username is taken"
                            usernameAvailable = false
                            usernameValidated = false
                        }
                    } else {
                        usernameError = true
                        usernameErrorMessage = "Error while checking username"
                        usernameAvailable = false
                        usernameValidated = false
                    }
                }
            }
        }
    }

    fun onDisplayNameChange(value: String) {
        displayName = value

        // Reset display name check fields
        displayNameError = false
        displayNameErrorMessage = ""
        displayNameValidated = false

        // Cancel ongoing check job
        displayNameCheckJob?.cancel()

        if (value.isEmpty()) return

        // Start a new check job
        displayNameCheckJob = viewModelScope.launch {
            delay(500)

            if (isActive) {
                if (value.length < 4) {
                    displayNameError = true
                    displayNameErrorMessage = "Display name must be at least 4 characters"
                    displayNameValidated = false
                } else if (value.length > 20) {
                    displayNameError = true
                    displayNameErrorMessage = "Display name must be at most 20 characters"
                    displayNameValidated = false
                } else {
                    displayNameError = false
                    displayNameErrorMessage = ""
                    displayNameValidated = true
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

        // Cancel ongoing check job
        passwordCheckJob?.cancel()

        if (value.isEmpty()) return

        // Start a new check job
        passwordCheckJob = viewModelScope.launch {
            delay(500)

            if (isActive) {
                if (value.length < 8) {
                    passwordError = true
                    passwordErrorMessage = "Password must be at least 8 characters"
                    passwordValidated = false
                } else if (value.length > 32) {
                    passwordError = true
                    passwordErrorMessage = "Password must be at most 32 characters"
                    passwordValidated = false
                } else {
                    passwordError = false
                    passwordErrorMessage = ""
                    passwordValidated = true
                    if (confirmPassword != "") {
                        validateConfirmPassword()
                    }
                }
            }
        }


    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword = value

        // Reset check fields
        confirmPasswordError = false
        confirmPasswordErrorMessage = ""
        confirmPasswordValidated = false

        // Cancel current check job
        confirmPasswordCheckJob?.cancel()

        if (value.isEmpty()) return

        // Start a new check job
        confirmPasswordCheckJob = viewModelScope.launch {
            delay(500)

            if (isActive) {
                if (!passwordError) {
                    validateConfirmPassword()
                }
            }
        }
    }

    private fun validateConfirmPassword(): Boolean {
        return if (password != confirmPassword) {
            confirmPasswordError = true
            confirmPasswordErrorMessage = "Password and confirm password does not match"
            confirmPasswordValidated = false
            false
        } else {
            confirmPasswordError = false
            confirmPasswordErrorMessage = ""
            confirmPasswordValidated = true
            true
        }
    }

    fun register() {
        viewModelScope.launch {
            registerStatus.emit(JobStatus.Processing)
            val result = userRepository.register(username, password, displayName)
            if (result.getOrDefault(false)) {
                registerErrorMessage = ""
                registerStatus.emit(JobStatus.Success)
            } else {
                registerErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                registerStatus.emit(JobStatus.Failure)
            }
        }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}