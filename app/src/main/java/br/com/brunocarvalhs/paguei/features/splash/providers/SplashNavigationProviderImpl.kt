package br.com.brunocarvalhs.paguei.features.splash.providers

import android.content.Context
import androidx.navigation.NavDeepLinkRequest
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.data.services.SessionManagerService
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.splash.commons.providers.SplashNavigateProvider

class SplashNavigationProviderImpl(
    context: Context,
    sessionManager: SessionManager = SessionManagerService()
) : SplashNavigateProvider {

    private val navigate: Navigation by lazy {
        Navigation(
            context = context,
            sessionManager = sessionManager
        )
    }

    override fun navigateToLoginRegister(): NavDeepLinkRequest =
        navigate.navigateToLoginRegister()

    override fun navigateToCosts(): NavDeepLinkRequest =
        navigate.navigateToCosts()
}