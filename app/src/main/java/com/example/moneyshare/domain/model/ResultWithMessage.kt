package com.example.moneyshare.domain.model

data class ResultWithMessage<T>(
    val result: T,
    val message: String?
)
