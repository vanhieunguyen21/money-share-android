package com.example.moneyshare.presentation.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.MainApplication
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val app: MainApplication,
) : ViewModel() {
    var profileImageUri: Uri? by mutableStateOf(null)
    var name: String by mutableStateOf("")

    var nameValidated: Boolean by mutableStateOf(false)
    var nameError: Boolean by mutableStateOf(false)
    var nameErrorMessage: String by mutableStateOf("")
    private var nameCheckJob: Job? = null

    val createGroupStatus = MutableSharedFlow<JobStatus>()
    var createGroupErrorMessage: String by mutableStateOf("")

    fun onProfileImageUriChange(value: Uri) {
        profileImageUri = value
    }

    fun onNameChange(value: String) {
        name = value

        // Cancel current check job
        nameCheckJob?.cancel()

        // Reset check fields
        nameValidated = false
        nameError = false
        nameErrorMessage = ""

        if (value.isEmpty()) return

        // Start a new check job
        nameCheckJob = viewModelScope.launch {
            delay(500)
            if (!isActive) return@launch
            if (value.length < 8) {
                nameError = true
                nameErrorMessage = "Group name must be at least 8 characters"
                nameValidated = false
            } else if (value.length > 32) {
                nameError = true
                nameErrorMessage = "Group name must be at most 32 characters"
                nameValidated = false
            } else {
                nameValidated = true
            }
        }
    }

    fun createGroup() {
        viewModelScope.launch {
            createGroupStatus.emit(JobStatus.Processing)
            val result = groupRepository.createGroup(name)
            if (result.isSuccess) {
                val createdGroup = result.getOrNull()
                if (createdGroup != null) app.newGroupFlow.emit(createdGroup)
                createGroupStatus.emit(JobStatus.Success)
            } else {
                createGroupErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                createGroupStatus.emit(JobStatus.Failure)
            }
        }
    }
}