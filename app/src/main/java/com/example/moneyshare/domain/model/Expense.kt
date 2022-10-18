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
    val purchaseTime: Instant = Instant.now(),
    val status: ExpenseStatus = ExpenseStatus.Pending,
    val owner: Member = Member(),
)
