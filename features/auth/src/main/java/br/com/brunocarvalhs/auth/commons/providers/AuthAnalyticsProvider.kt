package br.com.brunocarvalhs.auth.commons.providers

import br.com.brunocarvalhs.domain.services.AnalyticsService
import kotlin.reflect.KClass

interface AuthAnalyticsProvider {
    fun trackEvent(
        eventName: AnalyticsService.Events,
        eventPayload: Map<String?, Any?> = emptyMap(),
        eventPage: KClass<*>,
        customAttributes: Map<String, String>? = null
    )

    fun setUserId(id: String)

    fun trackScreenView(activityName: String, kClass: KClass<*>)
}