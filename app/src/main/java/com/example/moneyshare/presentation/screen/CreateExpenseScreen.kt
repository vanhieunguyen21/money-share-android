package com.example.moneyshare.presentation.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.provider.Settings
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.R
import com.example.moneyshare.domain.model.JobStatus
import com.example.moneyshare.domain.model.Role
import com.example.moneyshare.presentation.components.TextFieldWithError
import com.example.moneyshare.presentation.viewModel.CreateExpenseViewModel
import com.example.moneyshare.presentation.viewModel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

@Composable
fun CreateExpenseScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CreateExpenseViewModel = hiltViewModel(),
    groupViewModel: GroupViewModel = hiltViewModel()
) {
    var expenseOwnerExpanded by remember { mutableStateOf(false) }
    var showCreatingExpenseDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val allFieldsValidated by remember {
        derivedStateOf {
            viewModel.titleValidated && viewModel.amountValidated && viewModel.purchaseTimeValidated
        }
    }

    // Date and time picker dialog for purchase time
    val datePickerDialog = remember {
        val zonedTime = viewModel.purchaseTime.atZone(ZoneId.systemDefault())
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                viewModel.onPurchaseDateChange(day, month + 1, year)
            }, zonedTime.year, zonedTime.monthValue - 1, zonedTime.dayOfMonth
        )
    }
    val timePickerDialog = remember {
        val zonedTime = viewModel.purchaseTime.atZone(ZoneId.systemDefault())
        TimePickerDialog(
            context,
            { _: TimePicker, hour: Int, minute: Int ->
                viewModel.onPurchaseTimeChange(hour, minute)
            }, zonedTime.hour, zonedTime.minute, DateFormat.is24HourFormat(context)
        )
    }

    // Assign creator, owner and group in view model
    LaunchedEffect(groupViewModel.loggedInMember) {
        val member = groupViewModel.loggedInMember ?: return@LaunchedEffect
        val group = groupViewModel.group ?: return@LaunchedEffect
        viewModel.owner = member
        viewModel.creator = member
        viewModel.group = group
    }

    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Cancel",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Text(
                    text = "Create Expense",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colors.onPrimary
                )
                IconButton(onClick = viewModel::createExpense, enabled = allFieldsValidated) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_accepted),
                        contentDescription = "Apply",
                        tint = if (allFieldsValidated) MaterialTheme.colors.onPrimary else Color.Gray
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp)
        ) {
            TextFieldWithError(
                value = viewModel.title,
                onValueChange = viewModel::onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                isError = viewModel.titleError,
                errorMessage = viewModel.titleErrorMessage,
                label = "Title"
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldWithError(
                value = viewModel.description,
                onValueChange = viewModel::onDescriptionChange,
                modifier = Modifier.fillMaxWidth(),
                label = "Description"
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldWithError(
                value = viewModel.amountString,
                onValueChange = viewModel::onAmountChange,
                modifier = Modifier.fillMaxWidth(),
                isError = viewModel.amountError,
                errorMessage = viewModel.amountErrorMessage,
                label = "Amount",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Purchase date
            OutlinedTextField(
                value = viewModel.purchaseTime.let {
                    val zonedTime = it.atZone(ZoneId.systemDefault())
                    "%02d-%02d-%04d".format(
                        zonedTime.dayOfMonth,
                        zonedTime.monthValue,
                        zonedTime.year
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {},
                label = { Text(text = "Purchase Date") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_date),
                        contentDescription = "Choose Date",
                        modifier = Modifier.clickable { datePickerDialog.show() }
                    )
                },
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Purchase time
            OutlinedTextField(
                value = viewModel.purchaseTime.let {
                    val zonedTime = it.atZone(ZoneId.systemDefault())
                    if (DateFormat.is24HourFormat(context)) {
                        "%02d:%02d".format(zonedTime.hour, zonedTime.minute)
                    } else {
                        "%02d:%02d %s".format(
                            zonedTime.hour % 12,
                            zonedTime.minute,
                            if (zonedTime.hour <= 12) "AM" else "PM"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {},
                label = { Text(text = "Purchase Time") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Choose Time",
                        modifier = Modifier.clickable { timePickerDialog.show() }
                    )
                },
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Expense owner
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = viewModel.owner?.user?.displayName.orEmpty(),
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        // Only lets manager select owner of expense
                        if (groupViewModel.loggedInMember?.role == Role.Manager) {
                            IconButton(onClick = { expenseOwnerExpanded = !expenseOwnerExpanded }) {
                                Icon(
                                    imageVector = if (expenseOwnerExpanded) Icons.Filled.KeyboardArrowUp
                                    else Icons.Filled.KeyboardArrowDown,
                                    null,
                                )
                            }
                        }
                    }
                )
                // Only lets manager select owner of expense
                if (groupViewModel.loggedInMember?.role == Role.Manager) {
                    DropdownMenu(
                        expanded = expenseOwnerExpanded,
                        onDismissRequest = { expenseOwnerExpanded = false },
                        modifier = Modifier.width(maxWidth)
                    ) {
                        if (groupViewModel.group != null && groupViewModel.loggedInMember != null) {
                            groupViewModel.group?.members?.map {
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onOwnerChange(it)
                                        expenseOwnerExpanded = false
                                    },
                                ) {
                                    Text(text = it.user.displayName)
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    if (showCreatingExpenseDialog) {
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
                        text = "Creating expense...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

    // Listen to expense creation status
    LaunchedEffect(Unit) {
        viewModel.createExpenseStatus.collectLatest { status ->
            when (status) {
                JobStatus.Processing -> {
                    showCreatingExpenseDialog = true
                }
                JobStatus.Success -> {
                    showCreatingExpenseDialog = false
                    navController.popBackStack()
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(
                            "Expense \"${viewModel.title}\" created",
                            "Hide"
                        )
                    }
                }
                JobStatus.Failure -> {
                    showCreatingExpenseDialog = false
                    snackbarScope.launch {
                        if (viewModel.createExpenseErrorMessage.isEmpty())
                            snackbarHostState.showSnackbar("Create group failed", "Hide")
                        else snackbarHostState.showSnackbar(
                            viewModel.createExpenseErrorMessage, "Hide"
                        )
                    }
                }
                else -> {
                    showCreatingExpenseDialog = false
                }
            }
        }
    }

    // Listen to newly created expense to append to group expenses
    LaunchedEffect(Unit) {
        viewModel.createdExpense.collect { expense ->
            groupViewModel.appendExpense(expense)
        }
    }
}