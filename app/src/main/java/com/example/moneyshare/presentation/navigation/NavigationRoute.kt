package com.example.moneyshare.presentation.navigation

sealed class NavigationRoute(
    val route: String
) {
    object SplashScreen : NavigationRoute("splash")
    object LoginScreen : NavigationRoute("login")
    object RegisterScreen : NavigationRoute("register")
    object DashboardScreen : NavigationRoute("dashboard")
    object UserProfileScreen : NavigationRoute("userProfile")
    object EditUserProfileScreen : NavigationRoute("editUserProfile")
    object CreateGroupScreen : NavigationRoute("createGroup")

    object GroupNavigation : NavigationRoute("group/{groupID}") {
        fun routeWithArguments(groupID: Long): String {
            return "group/$groupID"
        }

        object GroupDashboardScreen : NavigationRoute("groupDashboard")
        object CreateExpenseScreen : NavigationRoute("createExpense")
    }
}
