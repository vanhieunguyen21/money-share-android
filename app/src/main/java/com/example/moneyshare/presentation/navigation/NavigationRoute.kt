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
    object GroupDashboardScreen : NavigationRoute("groupDashboard/{groupID}") {
        fun routeWithArgument(groupID: Long): String {
            return "groupDashboard/$groupID"
        }
    }
}
