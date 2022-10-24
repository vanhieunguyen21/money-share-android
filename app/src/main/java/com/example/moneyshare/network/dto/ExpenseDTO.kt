package com.example.moneyshare.network.dto

import com.example.moneyshare.domain.model.Expense
import com.example.moneyshare.domain.model.ExpenseStatus
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class ExpenseDTO(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("amount")
    val amount: Float? = null,
    @SerializedName("timestamp")
    val timestamp: Long? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("creatorID")
    val creatorID: Long? = null,
    @SerializedName("ownerID")
    val ownerID: Long? = null,
    @SerializedName("groupID")
    val groupID: Long? = null,
) {
    fun toExpense(): Expense {
        return Expense(
            id = id ?: 0L,
            title = title.orEmpty(),
            description = description,
            amount = amount ?: 0f,
            timestamp = timestamp?.let { Instant.ofEpochSecond(it) } ?: Instant.EPOCH,
            status = when (status) {
                null -> ExpenseStatus.Pending
                "pending" -> ExpenseStatus.Pending
                "approved" -> ExpenseStatus.Approved
                "denied" -> ExpenseStatus.Denied
                else -> ExpenseStatus.Pending
            },
            creatorID = creatorID ?: 0L,
            ownerID = ownerID ?: 0L,
            groupID = groupID ?: 0L,
        )
    }
}

fun Expense.toExpenseDTO(): ExpenseDTO {
    return ExpenseDTO(
        id = id,
        title = title,
        description = description,
        amount = amount,
        timestamp = timestamp.epochSecond,
        creatorID = creatorID,
        ownerID = ownerID,
        groupID = groupID,
    )
}
