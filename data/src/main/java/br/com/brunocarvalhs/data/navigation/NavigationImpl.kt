package br.com.brunocarvalhs.data.navigation

import androidx.navigation.NavController
import br.com.brunocarvalhs.payflow.domain.navigation.Navigation
import javax.inject.Inject

class NavigationImpl @Inject constructor(
    private val navController: NavController
) : Navigation {

    override fun navigateToGlobalAuth() {
        TODO("Not yet implemented")
    }

    override fun navigateToGlobalHome() {
        TODO("Not yet implemented")
    }

    override fun navigateToGlobalBilletRegistration() {
        TODO("Not yet implemented")
    }
}