package com.example.moneyshare.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.MainApplication
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.Expense
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.domain.model.Member
import com.example.moneyshare.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupRepository: GroupRepository,
    private val app: MainApplication,
) : ViewModel() {
    val loggedInUser by Auth.loggedInUser
    var loggedInMember: Member? by mutableStateOf(null)
    private val groupID: Long?
    var group: Group? by mutableStateOf(null)
    var groupLoading by mutableStateOf(false)
    var groupLoadingError by mutableStateOf(false)
    var groupLoadingErrorMessage by mutableStateOf("")
    var memberMap: Map<Long, Member>? = null

    init {
        groupID = savedStateHandle["groupID"]
        getGroupDetail()
    }

    private fun getGroupDetail() {
        groupID?.let {
            viewModelScope.launch {
                groupLoading = true
                val result = groupRepository.getGroupByID(it)
                if (result.isSuccess) {
                    val groupData = result.getOrNull()
                    // Process data, assign owner to expenses
                    groupData?.let { gr ->
                        memberMap = gr.members.associateBy { it.id }
                        val processedGroup = gr.copy(expenses = gr.expenses.map { expense ->
                            val member = memberMap?.get(expense.ownerID)
                            if (member != null) expense.copy(owner = member)
                            else expense
                        })
                        loggedInMember = memberMap?.get(loggedInUser?.id)
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

    fun appendExpense(expense: Expense) {
        val member = memberMap?.get(expense.ownerID)
        member?.let { m ->
            val newExpense = expense.copy(owner = m)
            group?.let { g ->
                val newExpenses = g.expenses.toMutableList()
                newExpenses.add(newExpense)
                group = g.copy(expenses = newExpenses)
            }
        }
    }
}