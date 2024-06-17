package br.com.brunocarvalhs.auth.commons.providers

import androidx.navigation.NavDeepLinkRequest

interface AuthNavigateProvider {
    fun navigateToCosts(): NavDeepLinkRequest
}