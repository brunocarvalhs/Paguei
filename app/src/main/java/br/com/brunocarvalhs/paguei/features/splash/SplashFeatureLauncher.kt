package br.com.brunocarvalhs.paguei.features.splash

import android.content.Context
import androidx.navigation.NavGraphBuilder
import br.com.brunocarvalhs.commons.navigation.NavigationItem
import br.com.brunocarvalhs.paguei.features.splash.providers.SplashAnalyticsProviderImpl
import br.com.brunocarvalhs.paguei.features.splash.providers.SplashNavigationProviderImpl
import br.com.brunocarvalhs.paguei.features.splash.providers.SplashNetworkProviderImpl
import br.com.brunocarvalhs.paguei.features.splash.providers.SplashSessionProviderImpl
import br.com.brunocarvalhs.splash.SplashDependencies
import br.com.brunocarvalhs.splash.SplashInitialization

class SplashFeatureLauncher(
    private val context: Context
) {

    private val dependencies: SplashDependencies by lazy {
        SplashDependencies(
            network = SplashNetworkProviderImpl(),
            navigation = SplashNavigationProviderImpl(context),
            analytics = SplashAnalyticsProviderImpl(context),
            session = SplashSessionProviderImpl()
        )
    }

    fun render(navGraphBuilder: NavGraphBuilder) {
        SplashInitialization.Builder()
            .setDependencies(dependencies)
            .build(navGraphBuilder, NavigationItem.Splash.route)
    }
}