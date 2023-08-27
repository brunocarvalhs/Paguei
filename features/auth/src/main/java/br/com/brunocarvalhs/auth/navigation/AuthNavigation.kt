package br.com.brunocarvalhs.auth.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.brunocarvalhs.auth.LoginViewModel
import br.com.brunocarvalhs.auth.ui.LoginScreen
import br.com.brunocarvalhs.commons.navigation.NavigationItem

fun NavGraphBuilder.authGraph(
    navController: NavController,
    route: String
) {
    navigation(
        startDestination = NavigationItem.Login.route,
        route = route
    ) {
        composable(
            route = NavigationItem.Login.route,
            arguments = NavigationItem.Login.arguments,
            deepLinks = NavigationItem.Login.deepLinks
        ) {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
        }
    }
}