package com.example.moneyshare.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.presentation.components.PasswordTextField
import com.example.moneyshare.presentation.components.TextFieldWithError
import com.example.moneyshare.presentation.navigation.NavigationRoute
import com.example.moneyshare.presentation.navigation.navigate
import com.example.moneyshare.presentation.theme.AppTheme
import com.example.moneyshare.presentation.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginStatus = viewModel.loginStatus.collectAsState(JobStatus.Idle)

    // Watch validation of all fields to enable or disable login button
    val allFieldsValidated by derivedStateOf {
        viewModel.usernameValidated && viewModel.passwordValidated
    }

    val localFocusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Login",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Username field
        TextFieldWithError(
            value = viewModel.username,
            onValueChange = viewModel::onUsernameChange,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally),
            label = "Username",
            isError = viewModel.usernameError,
            errorMessage = viewModel.usernameErrorMessage,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocusManager.clearFocus()
                }
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password field
        PasswordTextField(
            value = viewModel.password,
            isError = viewModel.passwordError,
            errorMessage = viewModel.passwordErrorMessage,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally),
            label = "Password",
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocusManager.clearFocus()
                }
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = viewModel::login,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.4f),
            enabled = allFieldsValidated
        ) {
            Text(text = "Login", color = MaterialTheme.colors.onPrimary)
        }

        Button(
            onClick = { navController.navigate(NavigationRoute.RegisterScreen.route) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.4f),
        ) {
            Text(text = "Register", color = MaterialTheme.colors.onPrimary)
        }
    }

    // Show indication on login status
    when (loginStatus.value) {
        JobStatus.Processing -> {
            Dialog(onDismissRequest = { }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.5f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(
                                Alignment.CenterHorizontally
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Logging in...",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

            }
        }
        JobStatus.Success -> {
            // Navigate to dashboard fragment
            LaunchedEffect(loginStatus) {
                navController.navigate(NavigationRoute.DashboardScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
        JobStatus.Failure -> {
            LaunchedEffect(loginStatus) {
                if (viewModel.loginErrorMessage.isEmpty()) {
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar("Login failed", "Hide")
                    }
                } else {
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(viewModel.loginErrorMessage, "Hide")
                    }
                }
            }
        }
        else -> {}
    }
}