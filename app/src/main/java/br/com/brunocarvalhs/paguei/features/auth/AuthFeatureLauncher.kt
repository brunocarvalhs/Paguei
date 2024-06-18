package br.com.brunocarvalhs.paguei.features.auth

import android.content.Context
import androidx.navigation.NavGraphBuilder
import br.com.brunocarvalhs.auth.AuthDependencies
import br.com.brunocarvalhs.auth.AuthInitialization
import br.com.brunocarvalhs.commons.navigation.NavigationItem
import br.com.brunocarvalhs.paguei.features.auth.providers.AuthAnalyticsProviderImpl
import br.com.brunocarvalhs.paguei.features.auth.providers.AuthNavigationProviderImpl
import br.com.brunocarvalhs.paguei.features.auth.providers.AuthNetworkProviderImpl
import br.com.brunocarvalhs.paguei.features.auth.providers.AuthSessionProviderImpl

class AuthFeatureLauncher(
    private val context: Context
) {

    private val dependencies: AuthDependencies by lazy {
        AuthDependencies(
            network = AuthNetworkProviderImpl(),
            navigation = AuthNavigationProviderImpl(context),
            analytics = AuthAnalyticsProviderImpl(context),
            session = AuthSessionProviderImpl()
        )
    }

    fun render(navGraphBuilder: NavGraphBuilder) {
        AuthInitialization.Builder()
            .setDependencies(dependencies)
            .build(navGraphBuilder, NavigationItem.Login.route)
    }
}