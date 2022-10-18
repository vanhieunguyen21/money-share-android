package com.example.moneyshare.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.moneyshare.R

@Composable
fun ExpandableFloatingActionButton(
    items: List<ExpandableFabItem>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotate: Float by animateFloatAsState(if (expanded) 135f else 0f)
    val density = LocalDensity.current

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically {
                with(density) { 40.dp.roundToPx() }
            } + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically {
                with(density) { 40.dp.roundToPx() }
            } + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                items.forEach { item ->
                    if (item.drawable != null)
                        FabWithLabel(
                            drawable = item.drawable,
                            label = item.label,
                            onClick = item.onClick,
                        )
                    else if (item.imageVector != null)
                        FabWithLabel(
                            icon = item.imageVector,
                            label = item.label,
                            onClick = item.onClick,
                        )
                    else FabWithLabel(
                        label = item.label,
                        onClick = item.onClick,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = modifier.rotate(rotate),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }

}

data class ExpandableFabItem(
    val imageVector: ImageVector? = null,
    @DrawableRes val drawable: Int? = null,
    val label: String? = null,
    val onClick: () -> Unit = {},
)

@Composable
fun FabWithLabel(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit = {},
) {
    FabWithLabel(
        label = label,
        modifier = modifier,
        onClick = onClick,
        icon = ImageVector.vectorResource(drawable)
    )
}

@Composable
fun FabWithLabel(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String? = null,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        label?.let {
            Card(
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            icon?.let { Icon(icon, null) }
        }
    }
}