package com.example.moneyshare.domain.model

data class Group(
    val id: Long,
    val groupIdentifier: String,
    val name: String,
    val totalExpense: Float = 0f,
    val averageExpense: Float = 0f,
    val members: List<Member> = listOf(),
    val expenses: List<Expense> = listOf(),
)
