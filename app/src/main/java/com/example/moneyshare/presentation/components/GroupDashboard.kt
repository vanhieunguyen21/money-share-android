package com.example.moneyshare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.moneyshare.domain.model.Group
import com.example.moneyshare.presentation.theme.*

@Composable
fun GroupDashboard(
    groups: List<Group>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    var selectedGroupIndex by remember { mutableStateOf<Int?>(null) }
    var dropDownExpanded by remember { mutableStateOf(false) }
    var selectedGroupName by remember { mutableStateOf("No group") }
    if (groups.isNotEmpty()) {
        selectedGroupIndex = 0
        selectedGroupName = groups[0].name.orEmpty()
    } else {
        selectedGroupIndex = null
        selectedGroupName = "No group"
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.fillMaxSize(),
        elevation = 8.dp
    ) {
        if (groups.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(8.dp)
            ) {
                // Group selection
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = "Group:",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterVertically)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.TopStart)
                    ) {
                        CustomTextField(
                            value = selectedGroupName,
                            onValueChange = { },
                            enabled = false,
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    if (dropDownExpanded) Icons.Filled.KeyboardArrowUp
                                    else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        dropDownExpanded = !dropDownExpanded
                                    }
                                )
                            },
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) { dropDownExpanded = !dropDownExpanded },
                            textStyle = MaterialTheme.typography.body1.copy(
                                color = MaterialTheme.colors.onSurface
                            )
                        )
                        DropdownMenu(
                            expanded = dropDownExpanded,
                            onDismissRequest = { dropDownExpanded = false }
                        ) {
                            groups.mapIndexed { index, group ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedGroupIndex = index
                                        selectedGroupName = group.name
                                        dropDownExpanded = false
                                    }, modifier = Modifier.height(36.dp)
                                ) {
                                    Text(group.name, maxLines = 1)
                                }
                            }
                            Divider()
                            DropdownMenuItem(
                                onClick = {
                                    dropDownExpanded = false
                                }, modifier = Modifier.height(36.dp)
                            ) {
                                Text(text = "Add group")
                            }
                        }
                    }
                }

//                GroupSummary(
//                    groups[selectedGroupIndex!!],
//                    modifier = Modifier.weight(1f)
//                )

                Button(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    colors = buttonColors(backgroundColor = Blue300)
                ) {
                    Text(text = "View Details", maxLines = 1)
                }
            }
        } else {
            Surface {

            }
        }
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    BasicTextField(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        singleLine = singleLine,
        maxLines = maxLines,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}