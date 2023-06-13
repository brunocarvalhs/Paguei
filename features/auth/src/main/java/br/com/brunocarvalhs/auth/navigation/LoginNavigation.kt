package br.com.brunocarvalhs.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.brunocarvalhs.auth.ui.LoginScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = "login", route = "login") {
        composable(
            route = "login",
        ) {
            LoginScreen(navController)
        }
    }
}
