package br.com.brunocarvalhs.commons.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

sealed class NavigationItem(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList(),
    val icon: ImageVector? = null,
) {

    companion object {
        const val URI = ""

        val START = Login.route

        fun to(
            navController: NavController,
            navigationItem: NavigationItem,
            builder: NavOptionsBuilder.() -> Unit
        ) {
            navController.navigate(navigationItem.route, builder)
        }

        val topLevelDestinations: List<NavigationItem> = listOf(
            Splash,
            Login,
            Home,
        )

        val bottomNavigation: List<NavigationItem> = listOf(
            Home,
        )
    }

    object Splash : NavigationItem(
        route = "splash",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/splash" }),
        arguments = listOf(navArgument("") { }),
    )

    object Login : NavigationItem(
        route = "login",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/login" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )

    object Home : NavigationItem(
        route = "home",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/home" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )

    object Extract : NavigationItem(
        route = "extracts",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/extracts" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )

    object Groups : NavigationItem(
        route = "groups",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/groups" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )

    object Profile : NavigationItem(
        route = "profile",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/profile" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )

    object Report : NavigationItem(
        route = "report",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/report" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )

    object CheckList : NavigationItem(
        route = "checklist",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/checklist" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )

    object Calculation : NavigationItem(
        route = "calculation",
        deepLinks = listOf(navDeepLink { uriPattern = "$URI/home" }),
        arguments = listOf(navArgument("") { }),
        icon = Icons.Filled.Person,
    )
}