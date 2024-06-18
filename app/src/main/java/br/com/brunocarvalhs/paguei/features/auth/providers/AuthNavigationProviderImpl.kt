package br.com.brunocarvalhs.paguei.features.auth.providers

import android.content.Context
import androidx.navigation.NavDeepLinkRequest
import br.com.brunocarvalhs.auth.commons.providers.AuthNavigateProvider
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.data.services.SessionManagerService
import br.com.brunocarvalhs.domain.services.SessionManager

class AuthNavigationProviderImpl(
    context: Context,
    sessionManager: SessionManager = SessionManagerService()
) : AuthNavigateProvider {

    private val navigate: Navigation by lazy {
        Navigation(
            context = context,
            sessionManager = sessionManager
        )
    }

    override fun navigateToCosts(): NavDeepLinkRequest {
        return navigate.navigateToCosts()
    }
}