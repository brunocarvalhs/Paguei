package br.com.brunocarvalhs.payflow.domain.navigation

import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities
import br.com.brunocarvalhs.payflow.domain.entities.UserEntities

interface Navigation {

    fun navigateToGlobalAuth()
    fun navigateToGlobalHome()
    fun navigateToGlobalBilletRegistration()

    interface AuthNavigation {
        fun navigateToHome(user: UserEntities)
    }

    interface HomeNavigation {
        fun navigateToLogin()
        fun navigateToModal(costs: CostsEntities)
        fun navigateToExtract()
        fun navigateToBilletRegistration(user: UserEntities)
    }

    interface BilletRegistration {
        fun navigateToManualRegistration(user: UserEntities)
        fun navigateToHome()
    }
}