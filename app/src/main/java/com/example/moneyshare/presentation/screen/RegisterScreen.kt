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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.R
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.presentation.components.PasswordTextField
import com.example.moneyshare.presentation.components.TextFieldWithError
import com.example.moneyshare.presentation.theme.AppTheme
import com.example.moneyshare.presentation.viewModel.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val registerStatus = viewModel.registerStatus.collectAsState(JobStatus.Idle)

    val allFieldsValidated by remember {
        derivedStateOf {
            viewModel.usernameValidated && viewModel.displayNameValidated &&
                    viewModel.passwordValidated && viewModel.confirmPasswordValidated
        }
    }

    val localFocusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Register",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                trailingIcon = {
                    if (viewModel.usernameChecking) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else if (viewModel.usernameError) {
                        Icon(
                            painterResource(id = R.drawable.ic_denied),
                            "",
                            tint = Color.Red
                        )
                    } else if (viewModel.usernameAvailable) {
                        Icon(
                            painterResource(id = R.drawable.ic_accepted),
                            "",
                            tint = Color.Green
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextFieldWithError(
                value = viewModel.displayName,
                onValueChange = viewModel::onDisplayNameChange,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                label = "Display Name",
                isError = viewModel.displayNameError,
                errorMessage = viewModel.displayNameErrorMessage,
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

            PasswordTextField(
                value = viewModel.confirmPassword,
                isError = viewModel.confirmPasswordError,
                errorMessage = viewModel.confirmPasswordErrorMessage,
                onValueChange = viewModel::onConfirmPasswordChange,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                label = "Confirm Password",
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.register() },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = allFieldsValidated
            ) {
                Text(text = "Register", color = MaterialTheme.colors.onPrimary)
            }
        }

        // Show indication on registering status
        when (registerStatus.value) {
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
                                text = "Registering...",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
            JobStatus.Success -> {
                LaunchedEffect(registerStatus.value) {
                    navController.popBackStack()
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(
                            "Register success, you can login now",
                            "Hide"
                        )
                    }
                }
            }
            JobStatus.Failure -> {
                LaunchedEffect(registerStatus.value) {
                    snackbarScope.launch {
                        if (viewModel.registerErrorMessage.isEmpty())
                            snackbarHostState.showSnackbar("Register failed", "Hide")
                        else snackbarHostState.showSnackbar(viewModel.registerErrorMessage, "Hide")
                    }
                }
            }
            else -> {}
        }
    }
}