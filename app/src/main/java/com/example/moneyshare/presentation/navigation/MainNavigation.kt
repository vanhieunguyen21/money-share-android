package com.example.moneyshare.presentation.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moneyshare.presentation.navigation.NavigationRoute.*
import com.example.moneyshare.presentation.screen.*
import kotlinx.coroutines.CoroutineScope

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
        composable(GroupDashboardScreen.route, arguments = listOf(
            navArgument("groupID") { type = NavType.LongType }
        )) {
            GroupDashboardScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                snackbarScope = snackbarScope
            )
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