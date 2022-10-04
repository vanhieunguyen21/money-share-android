package com.example.moneyshare.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.domain.model.Member
import kotlin.random.Random

@Composable
fun GroupSummary(
    group: Group,
    modifier: Modifier = Modifier,
) {
    val chartData: List<ChartData> = listOf()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isPortrait = maxWidth < maxHeight

        if (isPortrait) {
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                Row {
                    Text(
                        text = "Total spent:",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = "$520",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row {
                    Text(
                        text = "Your spent:",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = "$0",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                    )
                }

                Row {
                    Text(
                        text = "Your balance:",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = "-$120",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                    )
                }

                PieChart(
                    chartData = chartData,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        } else {
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Total spent:",
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = "$520",
                        style = MaterialTheme.typography.h6,
                    )

                    Text(
                        text = "Your spent:",
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = "$0",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = "Your balance:",
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = "-$120",
                        style = MaterialTheme.typography.h6,
                    )
                }

                PieChart(
                    chartData = chartData,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
        }
    }
}