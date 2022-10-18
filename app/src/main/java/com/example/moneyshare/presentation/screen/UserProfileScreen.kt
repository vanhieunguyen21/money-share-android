package com.example.moneyshare.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyshare.R
import com.example.moneyshare.constant.Constant.USER_PROFILE_IMAGE_BASE_URL
import com.example.moneyshare.presentation.navigation.NavigationRoute
import com.example.moneyshare.presentation.viewModel.UserProfileViewModel
import com.example.moneyshare.util.limitLength
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.ZoneId

@Composable
fun UserProfileScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    viewModel: UserProfileViewModel = hiltViewModel(),
) {
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
                    text = "User Profile",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) { padding ->
        viewModel.user?.let { user ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                GlideImage(
                    imageModel = { "$USER_PROFILE_IMAGE_BASE_URL${user.profileImageUrl}" },
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                        .align(Alignment.CenterHorizontally),
                    failure = { R.drawable.default_profile_image }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = user.displayName,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Username",
                        modifier = Modifier.wrapContentWidth(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = user.username,
                        modifier = Modifier.wrapContentWidth(Alignment.End)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Birthday",
                        modifier = Modifier.wrapContentWidth(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    user.dateOfBirth?.let { dateOfBirth ->
                        val zonedTime = dateOfBirth.atZone(ZoneId.of("UTC"))
                        Text(
                            text = "%02d-%02d-%04d".format(
                                zonedTime.dayOfMonth,
                                zonedTime.monthValue,
                                zonedTime.year
                            ),
                            modifier = Modifier.wrapContentWidth(Alignment.End)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Email",
                        modifier = Modifier.wrapContentWidth(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    user.emailAddress?.let {
                        Text(
                            text = it,
                            modifier = Modifier.wrapContentWidth(Alignment.End)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Phone Number",
                        modifier = Modifier.wrapContentWidth(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    user.phoneNumber?.let {
                        Text(
                            text = it,
                            modifier = Modifier.wrapContentWidth(Alignment.End)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navController.navigate(NavigationRoute.EditUserProfileScreen.route)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.4f)
                ) {
                    Text(text = "Edit Profile")
                }
                Button(
                    onClick = {
                        viewModel.logOut()
                        snackbarScope.launch {
                            snackbarHostState.showSnackbar("Logged out", "Hide")
                        }
                        navController.navigate(NavigationRoute.LoginScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.4f)
                ) {
                    Text(text = "Logout")
                }
            }
        }
    }
}