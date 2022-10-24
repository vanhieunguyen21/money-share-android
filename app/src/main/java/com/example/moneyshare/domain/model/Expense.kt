package com.example.moneyshare.domain.model

import java.time.Instant

enum class ExpenseStatus(val value: String) {
    Pending("Pending"), Approved("Approved"), Denied("Denied")
}

data class Expense(
    val id: Long = 0L,
    val title: String = "",
    val description: String? = null,
    val amount: Float = 0f,
    val timestamp: Instant = Instant.EPOCH,
    val status: ExpenseStatus = ExpenseStatus.Pending,
    val creatorID: Long = 0L,
    val creator: Member? = null,
    val ownerID: Long = 0L,
    val owner: Member? = null,
    val groupID: Long = 0L,
    val group: Group? = null,
)
