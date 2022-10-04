package com.example.moneyshare.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.domain.model.Member

val defaultColorPalette = listOf(
    Color(0xffe60049),
    Color(0xff0bb4ff),
    Color(0xff50e991),
    Color(0xffe6d800),
    Color(0xff9b19f5),
    Color(0xffffa300),
    Color(0xffdc0ab4),
    Color(0xffb3d4ff),
    Color(0xff00bfa0)
)

data class ChartData(
    val name: String,
    val value: Float
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PieChart(
    chartData: List<ChartData>,
    modifier: Modifier = Modifier,
    maximumElements: Int = 4,
    chartColor: List<Color>? = null
) {
    val colorPalette = chartColor?: defaultColorPalette

    val numberOfElements = remember(chartData) {
        if (chartData.size > maximumElements) maximumElements
        else chartData.size
    }

    val data: List<ChartData> = remember(chartData) {
        if (chartData.size > numberOfElements) {
            val result = mutableListOf<ChartData>()
            val sortedData = chartData.sortedByDescending { it.value }
            // Get first biggest n-1 items
            result.addAll(sortedData.slice(0 until maximumElements - 1))
            // Accumulate others value into "Other"
            val other = sortedData.slice(maximumElements until sortedData.size)
            val totalOtherValue = other.fold(0f) { acc, next -> acc + next.value }
            result.add(ChartData("Other", totalOtherValue))
            result
        } else {
            chartData.sortedByDescending { it.value }
        }
    }

    val totalValue = remember(chartData) {
        data.fold(0f) { acc, next -> acc + next.value }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isPortrait = maxWidth < maxHeight

        if (isPortrait) {
            Column {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .padding(8.dp)
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val pieSize =
                        if (canvasWidth > canvasHeight) canvasHeight
                        else canvasWidth

                    var startAngle = -90f

                    data.mapIndexed { index, item ->
                        val sweepAngle = item.value / totalValue * 360f

                        drawArc(
                            color = colorPalette[index],
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true,
                            topLeft = Offset(
                                (canvasWidth / 2f) - (pieSize / 2f),
                                (canvasHeight / 2f) - (pieSize / 2f)
                            ),
                            size = Size(pieSize, pieSize)
                        )
                        startAngle += sweepAngle
                    }
                }
                // Add overscroll configuration to disable overscroll animation
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        itemsIndexed(items = data) { index, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(colorPalette[index])
                                )
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(start = 8.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Row {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val pieSize =
                        if (canvasWidth > canvasHeight) canvasHeight
                        else canvasWidth

                    var startAngle = -90f

                    data.mapIndexed { index, item ->
                        val sweepAngle = item.value / totalValue * 360f

                        drawArc(
                            color = colorPalette[index],
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true,
                            topLeft = Offset(
                                (canvasWidth / 2f) - (pieSize / 2f),
                                (canvasHeight / 2f) - (pieSize / 2f)
                            ),
                            size = Size(pieSize, pieSize)
                        )
                        startAngle += sweepAngle
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(items = data) { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(colorPalette[index])
                            )
                            Text(
                                text = item.name,
                                modifier = Modifier.padding(start = 8.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}