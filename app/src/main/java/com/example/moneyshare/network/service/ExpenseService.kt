package com.example.moneyshare.network.service

import com.example.moneyshare.network.dto.ExpenseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ExpenseService {
    @POST("expense")
    suspend fun createExpense(
        @Header("Authorization") accessToken: String,
        @Body expense: ExpenseDTO
    ): Response<ExpenseDTO>
}