package com.example.moneyshare.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.MainApplication
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject constructor(
    private val groupRepository: GroupRepository,
    private val app: MainApplication,
) : ViewModel() {
    val user by Auth.loggedInUser

    var groups: List<Group> by mutableStateOf(listOf())
    val groupLoadStatus = MutableSharedFlow<JobStatus>()
    var groupLoadErrorMessage by mutableStateOf("")

    init {
        getUserGroups()
        // Subscribe to new group event
        viewModelScope.launch {
            app.newGroupFlow.collectLatest {
                val current = ArrayList(groups)
                current.add(it)
                groups = current
            }
        }
    }

    fun getUserGroups() {
        viewModelScope.launch {
            val user = user!!
            val result = groupRepository.getGroupByUser(user.id)
            if (result.isSuccess) {
                val data = result.getOrNull()
                if (data != null) {
                    groups = data
                }
                groupLoadStatus.emit(JobStatus.Success)
            } else {
                groupLoadErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                groupLoadStatus.emit(JobStatus.Failure)
            }
        }
    }

    fun logOut() {
        Auth.logOut()
    }
}