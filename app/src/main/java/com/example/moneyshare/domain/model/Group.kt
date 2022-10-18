package com.example.moneyshare.domain.model

data class Group(
    val id: Long = 0L,
    val name: String = "",
    val profileImageUrl: String? = null,
    val totalExpense: Float = 0f,
    val averageExpense: Float = 0f,
    val members: List<Member> = listOf(),
    val expenses: List<Expense> = listOf(),
)
