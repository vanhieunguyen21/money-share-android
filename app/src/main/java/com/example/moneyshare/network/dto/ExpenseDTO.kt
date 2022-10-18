package com.example.moneyshare.network.dto

import com.example.moneyshare.domain.model.Expense
import com.example.moneyshare.domain.model.ExpenseStatus
import com.example.moneyshare.domain.model.Member
import com.example.moneyshare.domain.model.User
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.Instant
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
    @SerializedName("purchaseTime")
    val purchaseTime: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("memberID")
    val memberID: Long? = null,
) {
    fun toExpense(): Expense {
        return Expense(
            id = id ?: 0L,
            title = title.orEmpty(),
            description = description,
            amount = amount ?: 0f,
            purchaseTime = purchaseTime?.let {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(it)?.toInstant()
            } ?: Instant.now(),
            status = when (status) {
                null -> ExpenseStatus.Pending
                "pending" -> ExpenseStatus.Pending
                "approved" -> ExpenseStatus.Approved
                "denied" -> ExpenseStatus.Denied
                else -> ExpenseStatus.Pending
            },
            owner = Member(User(id = memberID?: 0L))
        )
    }

}
