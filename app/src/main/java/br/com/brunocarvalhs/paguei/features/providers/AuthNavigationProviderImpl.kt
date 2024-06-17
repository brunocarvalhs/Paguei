package br.com.brunocarvalhs.paguei.features.providers

import android.content.Context
import androidx.navigation.NavDeepLinkRequest
import br.com.brunocarvalhs.auth.commons.providers.AuthNavigateProvider
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.data.services.SessionManagerService
import br.com.brunocarvalhs.domain.services.SessionManager

class AuthNavigationProviderImpl(
    private val context: Context,
    private val sessionManager: SessionManager = SessionManagerService()
) : AuthNavigateProvider {
    override fun navigateToCosts(): NavDeepLinkRequest {
        return Navigation(
            context = context,
            sessionManager = sessionManager
        ).navigateToCosts()
    }
}