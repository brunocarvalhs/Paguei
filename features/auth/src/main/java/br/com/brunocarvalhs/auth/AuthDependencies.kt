package br.com.brunocarvalhs.auth

import br.com.brunocarvalhs.auth.commons.providers.AuthAnalyticsProvider
import br.com.brunocarvalhs.auth.commons.providers.AuthNavigateProvider
import br.com.brunocarvalhs.auth.commons.providers.AuthNetworkProvider
import br.com.brunocarvalhs.auth.commons.providers.AuthSessionProvider

data class AuthDependencies(
     val analytics: AuthAnalyticsProvider,
     val navigation: AuthNavigateProvider,
     val session: AuthSessionProvider,
     val network: AuthNetworkProvider
)
