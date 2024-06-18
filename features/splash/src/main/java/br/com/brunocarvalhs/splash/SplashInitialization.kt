package br.com.brunocarvalhs.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import br.com.brunocarvalhs.splash.feature.presentation.content.SplashFragment
import kotlin.properties.Delegates

class SplashInitialization(builder: Builder) {

    init {
        SplashSession.dependencies = builder.dependencies
    }

    fun navigation(nav: NavGraphBuilder, route: String) {
        nav.fragment<SplashFragment>(route)
    }

    class Builder {
        internal var dependencies: SplashDependencies by Delegates.notNull()

        fun setDependencies(dependencies: SplashDependencies) = apply {
            this.dependencies = dependencies
        }

        fun build(nav: NavGraphBuilder, route: String) =
            SplashInitialization(builder = this).navigation(nav, route)
    }
}