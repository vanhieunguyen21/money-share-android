package com.example.moneyshare.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.moneyshare.auth.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor() : ViewModel() {
    val user by Auth.loggedInUser

    fun logOut() {
        Auth.logOut()
    }
}