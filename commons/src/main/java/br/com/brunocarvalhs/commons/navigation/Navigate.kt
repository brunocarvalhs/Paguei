package br.com.brunocarvalhs.commons.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavOptionsBuilder

enum class Navigate(
    override val route: String,
    override val arguments: List<NamedNavArgument> = emptyList(),
    override val deepLinks: List<NavDeepLink> = emptyList()
) : NavOptions {
    SPLASH(route = "splash"),
    AUTH(route = "auth"),
    COSTS(route = "costs"),
    EXTRACT(route = "extracts"),
    GROUPS(route = "groups"),
    PROFILE(route = "profile"),
    REPORT(route = "report"),
    CHECKLIST(route = "checklist"),
    CALCULATION(route = "calculation");

    companion object {
        val START = SPLASH.route

        fun to(
            navController: NavController,
            navigate: Navigate,
            builder: NavOptionsBuilder.() -> Unit
        ) {
            navController.navigate(navigate.route, builder)
        }
    }
}