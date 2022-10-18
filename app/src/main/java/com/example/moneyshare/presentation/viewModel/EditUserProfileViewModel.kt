package com.example.moneyshare.presentation.viewModel

import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.MainApplication
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class EditUserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val app: MainApplication
) : ViewModel() {
    var profileImageUrl: String? by mutableStateOf(null)
    var displayName: String? by mutableStateOf(null)
    var username: String? by mutableStateOf(null)
    var phoneNumber: String? by mutableStateOf(null)
    var emailAddress: String? by mutableStateOf(null)
    var dateOfBirth: Instant? by mutableStateOf(null)

    val updateProfileStatus = MutableSharedFlow<JobStatus>()
    var updateProfileErrorMessage by mutableStateOf("")

    var pickedProfileImageUri: Uri? = null
    var uploadProfileImageStatus = MutableSharedFlow<JobStatus>()
    var uploadProfileImageErrorMessage by mutableStateOf("")

    init {
        // Initialize field values with logged in user
        val user = Auth.loggedInUser.value
        user?.let {
            profileImageUrl = it.profileImageUrl
            displayName = it.displayName
            username = it.username
            phoneNumber = it.phoneNumber
            emailAddress = it.emailAddress
            dateOfBirth = it.dateOfBirth
        }
    }

    fun onDisplayNameChange(value: String) {
        displayName = value
    }

    fun onPhoneNumberChange(value: String?) {
        phoneNumber = value
    }

    fun onEmailAddressChange(value: String?) {
        emailAddress = value
    }

    fun onDateOfBirthChange(value: Instant?) {
        dateOfBirth = value
    }

    fun updateUser() {
        Auth.loggedInUser.value?.let {
            val fieldChanged = ((displayName != it.displayName) ||
                    (phoneNumber != it.phoneNumber) ||
                    (emailAddress != it.emailAddress) ||
                    (dateOfBirth != it.dateOfBirth))
            if (!fieldChanged) {
                viewModelScope.launch {
                    updateProfileErrorMessage = ""
                    updateProfileStatus.emit(JobStatus.Success)
                }
                return
            }

            viewModelScope.launch {
                updateProfileErrorMessage = ""
                updateProfileStatus.emit(JobStatus.Processing)
                val result = userRepository.updateUser(
                    it.id,
                    Auth.getAccessToken()!!,
                    displayName = if (displayName == it.displayName) null else displayName,
                    phoneNumber = if (phoneNumber == it.phoneNumber) null else phoneNumber,
                    emailAddress = if (emailAddress == it.emailAddress) null else emailAddress,
                    dateOfBirth = if (dateOfBirth == it.dateOfBirth) null else dateOfBirth,
                )
                if (result.isSuccess) {
                    val updatedUser = result.getOrNull()
                    if (updatedUser != null) {
                        Auth.saveUser(updatedUser)
                    }
                    updateProfileStatus.emit(JobStatus.Success)
                } else {
                    updateProfileErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                    updateProfileStatus.emit(JobStatus.Failure)
                }
            }
        }
    }

    fun uploadProfileImage() {
        val user = Auth.loggedInUser.value ?: return

        if (pickedProfileImageUri == null) {
            viewModelScope.launch {
                uploadProfileImageErrorMessage = "No file picked"
                uploadProfileImageStatus.emit(JobStatus.Failure)
            }
            return
        }

        try {
            val stream = app.contentResolver.openInputStream(pickedProfileImageUri!!)!!
            val fileCursor =
                app.contentResolver.query(pickedProfileImageUri!!,
                    null, null, null, null)!!
            val nameIndex = fileCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            fileCursor.moveToFirst()
            val fileName = fileCursor.getString(nameIndex)
            fileCursor.close()
            viewModelScope.launch {
                uploadProfileImageErrorMessage = ""
                uploadProfileImageStatus.emit(JobStatus.Processing)
                val result = userRepository.uploadUserProfileImage(
                    user.id,
                    Auth.getAccessToken()!!,
                    stream,
                    fileName,
                )
                if (result.isSuccess) {
                    val updatedUser = result.getOrNull()
                    if (updatedUser != null) {
                        Auth.saveUser(updatedUser)
                        profileImageUrl = updatedUser.profileImageUrl
                    }
                    uploadProfileImageStatus.emit(JobStatus.Success)
                } else {
                    uploadProfileImageErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                    uploadProfileImageStatus.emit(JobStatus.Failure)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            viewModelScope.launch {
                uploadProfileImageErrorMessage = e.message.orEmpty()
                uploadProfileImageStatus.emit(JobStatus.Failure)
            }
        }
    }
}