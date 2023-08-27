package br.com.brunocarvalhs.costs.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.brunocarvalhs.commons.navigation.NavigationItem
import br.com.brunocarvalhs.costs.ui.costs_list.CostsScreen
import br.com.brunocarvalhs.costs.ui.costs_list.CostsViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavController, route: String
) {
    navigation(
        startDestination = NavigationItem.Home.route,
        route = route
    ) {
        composable(
            route = NavigationItem.Home.route,
            arguments = NavigationItem.Home.arguments,
            deepLinks = NavigationItem.Home.deepLinks
        ) {
            val viewModel: CostsViewModel = viewModel()
            CostsScreen(navController = navController, viewModel = viewModel)
        }
    }
}