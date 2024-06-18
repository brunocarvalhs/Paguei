package br.com.brunocarvalhs.splash

import br.com.brunocarvalhs.splash.commons.providers.SplashAnalyticsProvider
import br.com.brunocarvalhs.splash.commons.providers.SplashNavigateProvider
import br.com.brunocarvalhs.splash.commons.providers.SplashNetworkProvider
import br.com.brunocarvalhs.splash.commons.providers.SplashSessionProvider


data class SplashDependencies(
     val analytics: SplashAnalyticsProvider,
     val navigation: SplashNavigateProvider,
     val session: SplashSessionProvider,
     val network: SplashNetworkProvider
)
