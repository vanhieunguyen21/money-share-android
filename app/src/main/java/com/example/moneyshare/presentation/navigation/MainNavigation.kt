package com.example.moneyshare.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.moneyshare.presentation.navigation.NavigationRoute.*
import com.example.moneyshare.presentation.screen.*
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun MainNavigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
) {
    NavHost(navController = navController, startDestination = SplashScreen.route) {
        composable(SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(LoginScreen.route) {
            LoginScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                snackbarScope = snackbarScope,
            )
        }
        composable(RegisterScreen.route) {
            RegisterScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                snackbarScope = snackbarScope,
            )
        }
        composable(DashboardScreen.route) {
            DashboardScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                snackbarScope = snackbarScope,
            )
        }
        composable(UserProfileScreen.route) {
            UserProfileScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                snackbarScope = snackbarScope,
            )
        }
        composable(EditUserProfileScreen.route) {
            EditUserProfileScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                snackbarScope = snackbarScope,
            )
        }
        composable(CreateGroupScreen.route) {
            CreateGroupScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                snackbarScope = snackbarScope
            )
        }
        navigation(
            route = GroupNavigation.route,
            startDestination = GroupNavigation.GroupDashboardScreen.route,
            arguments = listOf(navArgument("groupID") { type = NavType.LongType })
        ) {
            composable(GroupNavigation.GroupDashboardScreen.route) {
                // Get nested graph's back stack entry to create shared view model
                val parentEntry = remember {
                    navController.getBackStackEntry(GroupNavigation.route)
                }
                GroupDashboardScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    snackbarScope = snackbarScope,
                    viewModel = hiltViewModel(parentEntry)
                )
            }
            composable(GroupNavigation.CreateExpenseScreen.route) {
                // Get nested graph's back stack entry to create shared view model
                val parentEntry = remember {
                    navController.getBackStackEntry(GroupNavigation.route)
                }
                CreateExpenseScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    snackbarScope = snackbarScope,
                    groupViewModel = hiltViewModel(parentEntry)
                )
            }
        }
    }
}

// Extension for pop up to navigation
fun NavController.navigate(route: String, popUpTo: String, inclusive: Boolean) {
    navigate(route) {
        this.popUpTo(popUpTo) {
            this.inclusive = inclusive
        }
    }
}