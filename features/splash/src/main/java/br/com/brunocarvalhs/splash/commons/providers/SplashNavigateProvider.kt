package br.com.brunocarvalhs.splash.commons.providers

import androidx.navigation.NavDeepLinkRequest

interface SplashNavigateProvider {
    fun navigateToLoginRegister(): NavDeepLinkRequest
    fun navigateToCosts(): NavDeepLinkRequest
}
