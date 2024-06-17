package br.com.brunocarvalhs.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import br.com.brunocarvalhs.auth.feature.presentation.login.LoginFragment
import kotlin.properties.Delegates

class AuthInitialization(builder: Builder) {

    init {
        AuthSession.dependencies = builder.dependencies
    }

    fun navigation(nav: NavGraphBuilder, route: String) {
        nav.fragment<LoginFragment>(route) {
            label = "Login"
        }
    }

    class Builder {
        internal var dependencies: AuthDependencies by Delegates.notNull()

        fun setDependencies(dependencies: AuthDependencies) = apply {
            this.dependencies = dependencies
        }

        fun build(nav: NavGraphBuilder, route: String) =
            AuthInitialization(builder = this).navigation(nav, route)
    }
}