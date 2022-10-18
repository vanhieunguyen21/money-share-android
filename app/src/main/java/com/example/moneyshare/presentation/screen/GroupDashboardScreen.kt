package com.example.moneyshare.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.R
import com.example.moneyshare.presentation.components.GroupDetail
import com.example.moneyshare.presentation.viewModel.GroupDashboardViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun GroupDashboardScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: GroupDashboardViewModel = hiltViewModel()
) {
    viewModel.group?.let { group ->
        Scaffold(topBar = {
            TopAppBar {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(Icons.Filled.ArrowBack, null, modifier = Modifier.size(30.dp))
                }
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .weight(1f)
                )
            }
        }) { padding ->
            val p = padding
            GroupDetail(
                group,
                modifier = Modifier.fillMaxSize(),
                memberId = viewModel.loggedInUser?.id ?: 0L
            )
        }
    }

    if (viewModel.groupLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (viewModel.groupLoadingError) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text =
                if (viewModel.groupLoadingErrorMessage.isEmpty())
                    viewModel.groupLoadingErrorMessage else "Error loading group",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}