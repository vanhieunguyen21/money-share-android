package com.example.moneyshare.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.MainApplication
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDashboardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupRepository: GroupRepository,
    private val app: MainApplication,
) : ViewModel() {
    val loggedInUser by Auth.loggedInUser
    private val groupID: Long?
    var group: Group? by mutableStateOf(null)
    var groupLoading by mutableStateOf(false)
    var groupLoadingError by mutableStateOf(false)
    var groupLoadingErrorMessage by mutableStateOf("")

    init {
        groupID = savedStateHandle["groupID"]
        getGroupDetail()
    }

    private fun getGroupDetail() {
        groupID?.let {
            viewModelScope.launch {
                groupLoading = true
                val result = groupRepository.getGroupByID(groupID)
                if (result.isSuccess) {
                    val groupData = result.getOrNull()
                    // Process data, assign owner to expenses
                    groupData?.let { g ->
                        val memberMaps = g.members.associateBy { it.user.id }
                        val processedGroup = g.copy(expenses = g.expenses.map { expense ->
                            val member = memberMaps[expense.owner.user.id]
                            if (member != null) expense.copy(owner = member)
                            else expense
                        })
                        group = processedGroup
                    }
                } else {
                    groupLoadingErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                    groupLoadingError = true
                }
                groupLoading = false
            }
        }
    }
}