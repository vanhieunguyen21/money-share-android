package com.example.moneyshare.presentation.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.R
import com.example.moneyshare.constant.Constant
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.presentation.viewModel.EditUserProfileViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditUserProfileScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = rememberScaffoldState().snackbarHostState,
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: EditUserProfileViewModel = hiltViewModel(),
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    var showUpdatingProfileDialog by remember { mutableStateOf(false) }
    var showUpdatingProfileImageDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.pickedProfileImageUri = uri
            if (uri != null) viewModel.uploadProfileImage()
        }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val day = remember(viewModel.dateOfBirth) {
        if (viewModel.dateOfBirth != null) {
            val zonedTime = viewModel.dateOfBirth!!.atZone(ZoneId.of("UTC"))
            mutableStateOf(zonedTime.dayOfMonth)
        } else {
            val zonedTime = Instant.now().atZone(ZoneId.of("UTC"))
            mutableStateOf(zonedTime.dayOfMonth)
        }
    }
    val month = remember(viewModel.dateOfBirth) {
        if (viewModel.dateOfBirth != null) {
            val zonedTime = viewModel.dateOfBirth!!.atZone(ZoneId.of("UTC"))
            mutableStateOf(zonedTime.monthValue)
        } else {
            val zonedTime = Instant.now().atZone(ZoneId.of("UTC"))
            mutableStateOf(zonedTime.monthValue)
        }
    }
    val year = remember(viewModel.dateOfBirth) {
        if (viewModel.dateOfBirth != null) {
            val zonedTime = viewModel.dateOfBirth!!.atZone(ZoneId.of("UTC"))
            mutableStateOf(zonedTime.year)
        } else {
            val zonedTime = Instant.now().atZone(ZoneId.of("UTC"))
            mutableStateOf(zonedTime.year)
        }
    }

    val mDatePickerDialog = remember(year.value, month.value, day.value) {
        DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                val dateString =
                    "%04d-%02d-%02dT00:00:00Z".format(mYear, mMonth + 1, mDay)
                val newDate = Instant.parse(dateString)
                viewModel.onDateOfBirthChange(newDate)
            }, year.value, month.value - 1, day.value
        )
    }

    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colors.onPrimary
                )
                IconButton(onClick = { viewModel.updateUser() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_accepted),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        },
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            GlideImage(
                imageModel = { "${Constant.USER_PROFILE_IMAGE_BASE_URL}${viewModel.profileImageUrl}" },
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
                    .align(Alignment.CenterHorizontally),
                failure = { R.drawable.default_profile_image }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Change profile photo",
                modifier = Modifier
                    .clickable { galleryLauncher.launch("image/*") }
                    .align(Alignment.CenterHorizontally),
                color = Color.Cyan,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = viewModel.displayName.orEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onValueChange = viewModel::onDisplayNameChange,
                label = { Text(text = "Display Name") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.username.orEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {},
                label = { Text(text = "Username") },
                enabled = false,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.phoneNumber.orEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onValueChange = viewModel::onPhoneNumberChange,
                label = { Text(text = "Phone Number") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.emailAddress.orEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onValueChange = viewModel::onEmailAddressChange,
                label = { Text(text = "Email Address") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    softwareKeyboardController?.hide()
                    localFocusManager.clearFocus()
                })
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value =
                if (viewModel.dateOfBirth != null) {
                    val zonedTime = viewModel.dateOfBirth!!.atZone(ZoneId.of("UTC"))
                    "%02d-%02d-%04d".format(
                        zonedTime.dayOfMonth,
                        zonedTime.monthValue,
                        zonedTime.year
                    )
                } else "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {},
                label = { Text(text = "Date of Birth") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_date),
                        contentDescription = "",
                        modifier = Modifier.clickable { mDatePickerDialog.show() }
                    )
                },
                maxLines = 1,
            )
        }
    }

    if (showUpdatingProfileDialog) {
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
                        text = "Updating profile...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    } else if (showUpdatingProfileImageDialog) {
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
                        text = "Uploading profile image...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uploadProfileImageStatus.collectLatest { status ->
            when (status) {
                JobStatus.Processing -> {
                    showUpdatingProfileImageDialog = true
                }
                JobStatus.Success -> {
                    showUpdatingProfileImageDialog = false
                    navController.popBackStack()
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar("Profile updated", "Hide")
                    }
                }
                JobStatus.Failure -> {
                    showUpdatingProfileImageDialog = false
                    snackbarScope.launch {
                        if (viewModel.updateProfileErrorMessage.isEmpty())
                            snackbarHostState.showSnackbar("Update profile failed", "Hide")
                        else snackbarHostState.showSnackbar(
                            viewModel.updateProfileErrorMessage, "Hide"
                        )
                    }
                }
                else -> {
                    showUpdatingProfileImageDialog = false
                }
            }
        }

        viewModel.updateProfileStatus.collectLatest { status ->
            when (status) {
                JobStatus.Processing -> {
                    showUpdatingProfileDialog = true
                }
                JobStatus.Success -> {
                    showUpdatingProfileDialog = false
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar("Profile image updated", "Hide")
                    }

                }
                JobStatus.Failure -> {
                    showUpdatingProfileDialog = false
                    snackbarScope.launch {
                        if (viewModel.uploadProfileImageErrorMessage.isEmpty())
                            snackbarHostState.showSnackbar("Update profile image failed", "Hide")
                        else snackbarHostState.showSnackbar(
                            viewModel.uploadProfileImageErrorMessage,
                            "Hide"
                        )
                    }

                }
                else -> {
                    showUpdatingProfileDialog = false
                }
            }
        }
    }
}