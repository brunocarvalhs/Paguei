package br.com.brunocarvalhs.auth

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.brunocarvalhs.auth.ui.LoginScreen
import br.com.brunocarvalhs.commons.navigation.NavOptions

fun NavGraphBuilder.authGraph(
    navController: NavController,
    route: String
) {
    navigation(
        startDestination = AuthNavOptions.START_DESTINATION,
        route = route
    ) {
        composable(
            route = AuthNavOptions.Login().route,
            arguments = AuthNavOptions.Login().arguments,
            deepLinks = AuthNavOptions.Login().deepLinks
        ) {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
        }
    }
}


sealed class AuthNavOptions : NavOptions {

    companion object {
        val START_DESTINATION = Login().route
    }

    data class Login(
        override val route: String = "login",
        override val arguments: List<NamedNavArgument> = emptyList(),
        override val deepLinks: List<NavDeepLink> = emptyList()
    ) : NavOptions
}