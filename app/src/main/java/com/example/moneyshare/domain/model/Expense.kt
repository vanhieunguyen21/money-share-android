package com.example.moneyshare.domain.model

import java.time.Instant

enum class ExpenseStatus(val value: String) {
    Pending("Pending"), Accepted("Accepted"), Denied("Denied")
}

data class Expense(
    val id: Long,
    val title: String,
    val description: String? = null,
    val amount: Float,
    val purchaseTime: Instant,
    val status: ExpenseStatus = ExpenseStatus.Pending,
    val owner: Member,
)
