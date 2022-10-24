package com.example.moneyshare.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.R
import com.example.moneyshare.constant.Constant.USER_PROFILE_IMAGE_BASE_URL
import com.example.moneyshare.constant.getUserProfileImageLink
import com.example.moneyshare.presentation.components.ExpandableFabItem
import com.example.moneyshare.presentation.components.ExpandableFloatingActionButton
import com.example.moneyshare.presentation.components.GroupItem
import com.example.moneyshare.presentation.navigation.NavigationRoute
import com.example.moneyshare.presentation.viewModel.DashboardViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope

@Composable
fun DashboardScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val items = listOf(
        ExpandableFabItem(
            drawable = R.drawable.ic_people,
            label = "Create Group",
            onClick = { navController.navigate(NavigationRoute.CreateGroupScreen.route) }),
        ExpandableFabItem(drawable = R.drawable.ic_person_add, label = "Join Group", onClick = {}),
    )

    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(Icons.Filled.Menu, null, modifier = Modifier.size(30.dp))
                }
                Text(
                    text = stringResource(R.string.dashboard),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .weight(1f)
                )
                GlideImage(
                    imageModel = {
                        if (viewModel.user != null && viewModel.user!!.profileImageUrl != null)
                            getUserProfileImageLink(viewModel.user!!.profileImageUrl!!)
                        else R.drawable.default_profile_image
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colors.onPrimary, CircleShape)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navController.navigate(NavigationRoute.UserProfileScreen.route)
                        },
                )
            }
        },
        floatingActionButton = {
            ExpandableFloatingActionButton(items)
        }
    ) { padding ->
        // List of groups
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            Text(
                text = "Your Groups",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h5
            )
            LazyColumn {
                items(viewModel.groups) { group ->
                    Divider()
                    GroupItem(group, modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable {
                            navController.navigate(
                                NavigationRoute.GroupNavigation.routeWithArguments(
                                    group.id
                                )
                            )
                        })
                }
            }
        }
    }
}