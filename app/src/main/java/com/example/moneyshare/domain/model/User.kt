package com.example.moneyshare.domain.model

import java.time.Instant

data class User(
    val id: Long,
    val username: String,
    val displayName: String,
    val phoneNumber: String? = null,
    val emailAddress: String? = null,
    val dateOfBirth: Instant? = null,
)
