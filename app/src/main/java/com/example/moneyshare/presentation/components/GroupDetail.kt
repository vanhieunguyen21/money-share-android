package com.example.moneyshare.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.example.moneyshare.R
import com.example.moneyshare.domain.model.Expense
import com.example.moneyshare.domain.model.ExpenseStatus
import com.example.moneyshare.domain.model.Group
import java.text.DateFormat
import java.util.*

@Composable
fun GroupDetail(
    group: Group,
    modifier: Modifier = Modifier,
    memberId: Long = 0L,
) {
    var memberExpense by remember(group, memberId) {
        mutableStateOf(group.members.find { it.user.id == memberId }?.totalExpense)
    }

    Column(modifier = modifier) {
        // Group statistics
        Card(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 8.dp,
            modifier = Modifier.padding(10.dp)
        ) {
            Row(modifier = Modifier.padding(0.dp, 10.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Your\nExpense",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$%.2f".format(memberExpense),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp)
                        .background(Color.LightGray)
                        .align(Alignment.CenterVertically)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Group\nExpense",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$%.2f".format(group.totalExpense),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp)
                        .background(Color.LightGray)
                        .align(Alignment.CenterVertically)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Average\nExpense",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$%.2f".format(group.averageExpense),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Functionality

        // Expense history
        Text(
            text = "Expense History",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        LazyColumn {
            items(group.expenses) { expense ->
                ExpenseItem(expense)
                Divider()
            }
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    modifier: Modifier = Modifier,
    isManager: Boolean = false,
    showStatus: Boolean = false,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .clickable { isExpanded = !isExpanded }
    ) {
        Row(
            modifier = Modifier.height(80.dp)
        ) {
            GlideImage(
                imageModel = ImageBitmap
                    .imageResource(id = R.drawable.default_profile_image)
                    .asAndroidBitmap(),
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp)
                    .clip(CircleShape),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = expense.title,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                    )
                    if (showStatus) {
                        ExpenseStatusCard(
                            status = expense.status,
                            modifier = Modifier
                                .wrapContentWidth(Alignment.End)
                                .width(60.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    Text(
                        text = "$%.2f".format(expense.amount),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .align(Alignment.Top)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = DateFormat.getDateInstance().format(Date.from(expense.purchaseTime)),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.Top)
                    )
                }
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Column {
                Row(modifier = Modifier.padding(10.dp, 0.dp)) {
                    Text(
                        text = "From: ",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = expense.owner.user.displayName,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = DateFormat.getTimeInstance().format(Date.from(expense.purchaseTime)),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                    )
                }
                expense.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(10.dp, 0.dp)
                    )
                }

                // Show manage button as manager
                if (isManager) {
                    if (expense.status == ExpenseStatus.Pending) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = { /*TODO*/ }, modifier = Modifier.width(100.dp)) {
                                Text(text = "Accept", maxLines = 1)
                            }
                            Spacer(modifier = Modifier.weight(0.3f))
                            Button(onClick = { /*TODO*/ }, modifier = Modifier.width(100.dp)) {
                                Text(text = "Deny", maxLines = 1)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseStatusCard(
    status: ExpenseStatus,
    modifier: Modifier = Modifier,
) {
    val color = remember {
        when (status) {
            ExpenseStatus.Pending -> Color.Yellow
            ExpenseStatus.Approved -> Color.Green
            ExpenseStatus.Denied -> Color.Red
        }
    }

    Card(
        modifier = modifier.clip(MaterialTheme.shapes.small)
    ) {
        Text(
            text = status.value,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .background(color = color)
                .padding(2.dp),
            textAlign = TextAlign.Center
        )
    }
}