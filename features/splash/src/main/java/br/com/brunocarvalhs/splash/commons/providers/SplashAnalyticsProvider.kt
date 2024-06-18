package br.com.brunocarvalhs.splash.commons.providers

import kotlin.reflect.KClass

interface SplashAnalyticsProvider {
    fun setUserId(id: String)
    fun trackScreenView(activityName: String, kClass: KClass<*>)
}
