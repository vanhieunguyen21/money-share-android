package com.example.moneyshare.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyshare.domain.model.Expense
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.domain.model.Member
import com.example.moneyshare.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {
    var creator: Member? = null
    var group: Group? = null

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var amountString by mutableStateOf("0")
    private var amount = 0f
    var purchaseTime: Instant by mutableStateOf(Instant.now())
    var owner: Member? by mutableStateOf(null)

    // Title check fields
    var titleValidated by mutableStateOf(false)
    var titleError by mutableStateOf(false)
    var titleErrorMessage by mutableStateOf("")

    // Amount check fields
    var amountValidated by mutableStateOf(true)
    var amountError by mutableStateOf(false)
    var amountErrorMessage by mutableStateOf("")

    // Purchase day check fields
    var purchaseTimeValidated by mutableStateOf(true)
    var purchaseTimeError by mutableStateOf(false)
    var purchaseTimeErrorMessage by mutableStateOf("")

    // Create expense status fields
    var createExpenseStatus = MutableSharedFlow<JobStatus>()
    var createExpenseErrorMessage by mutableStateOf("")
    var createdExpense = MutableSharedFlow<Expense>()

    fun onTitleChange(value: String) {
        title = value

        if (value.isEmpty()) {
            titleValidated = false
            titleError = false
            titleErrorMessage = ""
        } else {
            titleValidated = true
            titleError = false
            titleErrorMessage = ""
        }
    }

    fun onDescriptionChange(value: String) {
        description = value
    }

    fun onAmountChange(value: String) {
        amountString = value
        if (value.isEmpty()) {
            amountValidated = false
            amountError = false
            amountErrorMessage = ""
            return
        }

        try {
            amount = value.toFloat()
            if (amount < 0) {
                amountValidated = false
                amountError = true
                amountErrorMessage = "Amount cannot be less than 0"
            } else {
                amountValidated = true
                amountError = false
                amountErrorMessage = ""
            }
        } catch (ne: NumberFormatException) {
            ne.printStackTrace()
            amountValidated = false
            amountError = true
            amountErrorMessage = "Only numbers accepted"
        }
    }

    fun onPurchaseDateChange(day: Int, month: Int, year: Int) {
        purchaseTime = purchaseTime.atZone(ZoneId.systemDefault())
            .withDayOfMonth(day)
            .withMonth(month)
            .withYear(year)
            .toInstant()
    }

    fun onPurchaseTimeChange(hour: Int, minute: Int) {
        purchaseTime = purchaseTime.atZone(ZoneId.systemDefault())
            .withHour(hour)
            .withMinute(minute)
            .toInstant()
    }

    fun onOwnerChange(value: Member) {
        owner = value
    }

    fun createExpense() {
        val owner = owner ?: return
        val creator = creator ?: return
        val group = group ?: return
        val newExpense = Expense(
            title = title,
            description = description,
            amount = amount,
            timestamp = purchaseTime,
            ownerID = owner.id,
            creatorID = creator.id,
            groupID = group.id,
        )
        viewModelScope.launch {
            createExpenseStatus.emit(JobStatus.Processing)
            val result = expenseRepository.createExpense(newExpense)
            if (result.isSuccess) {
                val expense = result.getOrNull()
                if (expense != null) {
                    createdExpense.emit(expense)
                }
                createExpenseErrorMessage = ""
                createExpenseStatus.emit(JobStatus.Success)
            } else {
                createExpenseErrorMessage = result.exceptionOrNull()?.message.orEmpty()
                createExpenseStatus.emit(JobStatus.Failure)
            }
        }

    }
}