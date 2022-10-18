package com.example.moneyshare.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.moneyshare.R

@Composable
fun PasswordTextField(
    value: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = "",
    onValueChange: (String) -> Unit,
    label: String = "",
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions { }
) {
    var isShowPassword by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            label = { Text(label) },
            isError = isError,
            trailingIcon = {
                IconButton(onClick = { isShowPassword = !isShowPassword }) {
                    when (isShowPassword) {
                        true -> {
                            Icon(
                                painter = painterResource(R.drawable.ic_password_visible),
                                contentDescription = ""
                            )
                        }

                        false -> {
                            Icon(
                                painter = painterResource(R.drawable.ic_password_not_visible),
                                contentDescription = ""
                            )
                        }
                    }
                }
            },
            visualTransformation = when (isShowPassword) {
                true -> VisualTransformation.None
                false -> PasswordVisualTransformation()
            },
        )
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.caption,
            )
        }
    }
}