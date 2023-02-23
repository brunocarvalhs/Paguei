package br.com.brunocarvalhs.domain.services

import kotlin.reflect.KClass

interface AnalyticsService {
    fun trackEvent(
        eventName: String,
        eventPayload: Map<String, Any>,
        eventPage: KClass<*>,
        eventValue: Double?,
        customAttributes: Map<String, String>?
    )

    fun trackUserEvent(
        userId: String,
        eventAction: String,
        eventValue: Double?,
        customAttributes: Map<String, String>?
    )

    fun trackScreenView(screenName: String, screenClass: KClass<*>)
    fun setUserProperty(name: String, value: String?)
    fun setUserId(userId: String)
}