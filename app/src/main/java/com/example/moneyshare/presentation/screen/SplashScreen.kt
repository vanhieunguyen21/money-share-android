package com.example.moneyshare.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.presentation.navigation.NavigationRoute
import com.example.moneyshare.presentation.navigation.navigate
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController
) {

    LaunchedEffect(true) {
//        delay(1000)

        if (isActive) {
            if (Auth.isLoggedIn()) {
                navController.navigate(NavigationRoute.DashboardScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                navController.navigate(NavigationRoute.LoginScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Splash",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h2,
        )
    }
}