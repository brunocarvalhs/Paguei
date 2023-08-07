package br.com.brunocarvalhs.commons.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

interface NavOptions {
    val route: String
    val arguments: List<NamedNavArgument>
    val deepLinks: List<NavDeepLink>
}
