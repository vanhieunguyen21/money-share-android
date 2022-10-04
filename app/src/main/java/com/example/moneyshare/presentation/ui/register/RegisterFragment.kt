package com.example.moneyshare.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moneyshare.presentation.components.PasswordTextField
import com.example.moneyshare.presentation.theme.AppTheme
import com.example.moneyshare.R
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalComposeUiApi::class)
@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        val username = viewModel.username.value
                        val usernameError = viewModel.usernameError.value
                        val usernameAvailable = viewModel.usernameAvailable.value
                        val userNameChecking = viewModel.usernameChecking.value

                        val password = viewModel.password.value
                        val confirmPassword = viewModel.confirmPassword.value

                        val localFocusManager = LocalFocusManager.current

                        OutlinedTextField(
                            value = username,
                            onValueChange = viewModel::onUsernameChange,
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text("Username") },
                            singleLine = true,
                            isError = usernameError,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = false,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    localFocusManager.clearFocus()
                                }
                            ),
                            trailingIcon = {
                                if (userNameChecking) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                } else if (usernameError) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_denied),
                                        "",
                                        tint = Color.Red
                                    )
                                } else if (usernameAvailable) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_accepted),
                                        "",
                                        tint = Color.Green
                                    )
                                }
                            }
                        )
                        if (usernameError && viewModel.usernameErrorMessage.isNotEmpty()) {
                            Text(text = viewModel.usernameErrorMessage, color = Color.Red)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        PasswordTextField(
                            value = password,
                            onValueChange = viewModel::onPasswordChange,
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = "Password",
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    localFocusManager.clearFocus()
                                }
                            ),
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        PasswordTextField(
                            value = confirmPassword,
                            onValueChange = viewModel::onConfirmPasswordChange,
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = "Confirm Password",
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    localFocusManager.clearFocus()
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}