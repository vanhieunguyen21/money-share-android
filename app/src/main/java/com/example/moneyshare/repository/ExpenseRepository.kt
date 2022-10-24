package com.example.moneyshare.repository

import com.example.moneyshare.domain.model.Expense

interface ExpenseRepository {
    suspend fun createExpense(expense: Expense) : Result<Expense>
}