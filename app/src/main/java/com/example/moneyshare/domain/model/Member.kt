package com.example.moneyshare.domain.model

enum class Role(value: String) {
    Member("member"), Manager("manager")
}

data class Member(
    val user: User,
    val role: Role,
    val totalExpense: Float = 0f,
    val expenses: List<Expense> = listOf(),
)
