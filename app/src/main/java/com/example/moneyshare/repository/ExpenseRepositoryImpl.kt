package com.example.moneyshare.repository

import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.Expense
import com.example.moneyshare.network.dto.ExpenseDTO
import com.example.moneyshare.network.dto.toExpenseDTO
import com.example.moneyshare.network.response.ErrorResponse
import com.example.moneyshare.network.service.ExpenseService
import com.google.gson.Gson
import retrofit2.Response

class ExpenseRepositoryImpl(
    private val expenseService: ExpenseService
) : ExpenseRepository {
    override suspend fun createExpense(expense: Expense): Result<Expense> {
        val request = expense.toExpenseDTO()
        val response: Response<ExpenseDTO>
        try {
            response = expenseService.createExpense(Auth.getAccessToken()!!, request)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) Result.success(body.toExpense())
            else Result.failure(Exception("Response body is empty"))
        } else {
            val errorBody =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.failure(Exception(errorBody.message))
        }
    }
}