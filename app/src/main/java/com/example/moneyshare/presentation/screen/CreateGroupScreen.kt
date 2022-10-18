package com.example.moneyshare.presentation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.R
import com.example.moneyshare.constant.Constant
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.presentation.components.TextFieldWithError
import com.example.moneyshare.presentation.viewModel.CreateGroupViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CreateGroupScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = rememberScaffoldState().snackbarHostState,
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CreateGroupViewModel = hiltViewModel()
) {
    var showCreatingGroupDialog by remember { mutableStateOf(false) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.profileImageUri = uri
        }

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
                    text = "Create Group",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colors.onPrimary
                )
                IconButton(onClick = viewModel::createGroup) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_accepted),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            GlideImage(
                imageModel = {
                    viewModel.profileImageUri ?: R.drawable.default_group_profile_image
                },
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .clickable { galleryLauncher.launch("image/*") },
                failure = { R.drawable.default_group_profile_image }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tap to change group picture",
                style = MaterialTheme.typography.caption,
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextFieldWithError(
                value = viewModel.name,
                onValueChange = viewModel::onNameChange,
                label = "Group Name",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                isError = viewModel.nameError,
                errorMessage = viewModel.nameErrorMessage
            )
        }
    }

    if (showCreatingGroupDialog) {
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
                        text = "Creating group...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.createGroupStatus.collectLatest { status ->
            when (status) {
                JobStatus.Processing -> {
                    showCreatingGroupDialog = true
                }
                JobStatus.Success -> {
                    showCreatingGroupDialog = false
                    navController.popBackStack()
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(
                            "Group \"${viewModel.name}\" created",
                            "Hide"
                        )
                    }
                }
                JobStatus.Failure -> {
                    showCreatingGroupDialog = false
                    snackbarScope.launch {
                        if (viewModel.createGroupErrorMessage.isEmpty())
                            snackbarHostState.showSnackbar("Create group failed", "Hide")
                        else snackbarHostState.showSnackbar(
                            viewModel.createGroupErrorMessage, "Hide"
                        )
                    }
                }
                else -> {
                    showCreatingGroupDialog = false
                }
            }
        }
    }
}