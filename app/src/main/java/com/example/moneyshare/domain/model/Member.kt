package com.example.moneyshare.domain.model

enum class Role(value: String) {
    Member("Member"), Manager("Manager")
}

data class Member(
    val user: User = User(),
    val role: Role = Role.Member,
    val totalExpense: Float = 0f,
    val expenses: List<Expense> = listOf(),
)
