package br.com.brunocarvalhs.data.services

import android.os.Bundle
import br.com.brunocarvalhs.domain.services.AnalyticsService
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import kotlin.reflect.KClass

class AnalyticsServiceImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val dataStoreService: DataStoreService
) : AnalyticsService {

    private fun isPermissionAnalytics() = dataStoreService.get("analytics", true)

    override fun trackEvent(
        eventName: AnalyticsService.Events,
        eventPayload: Map<String?, Any?>,
        eventPage: KClass<*>,
        eventValue: Double?,
        customAttributes: Map<String, String>?
    ) {
        val bundle = Bundle()
        for ((key, value) in eventPayload) {
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Double -> bundle.putDouble(key, value)
                is Long -> bundle.putLong(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                else -> bundle.putString(key, value.toString())
            }
        }
        customAttributes?.forEach { (key, value) ->
            bundle.putString(key, value)
        }
        eventValue?.let { bundle.putDouble(FirebaseAnalytics.Param.VALUE, it) }
        val name = convertEventToFirebaseEvent(eventName)
        if (isPermissionAnalytics()) firebaseAnalytics.logEvent(name, bundle)
    }

    override fun trackEvent(
        eventName: String,
        eventPayload: Map<String?, Any?>,
        eventPage: KClass<*>,
        eventValue: Double?,
        customAttributes: Map<String, String>?
    ) {
        val bundle = Bundle()
        for ((key, value) in eventPayload) {
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Double -> bundle.putDouble(key, value)
                is Long -> bundle.putLong(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                else -> bundle.putString(key, value.toString())
            }
        }
        customAttributes?.forEach { (key, value) ->
            bundle.putString(key, value)
        }
        eventValue?.let { bundle.putDouble(FirebaseAnalytics.Param.VALUE, it) }
        if (isPermissionAnalytics()) firebaseAnalytics.logEvent(eventName, bundle)
    }

    override fun trackUserEvent(
        userId: String,
        eventAction: String,
        eventValue: Double?,
        customAttributes: Map<String, String>?
    ) {
        val eventName = "UserAnalyticsEvent"
        val eventPayload = mapOf<String?, Any?>(
            "user_id" to userId,
            "event_action" to eventAction
        )
        trackEvent(eventName, eventPayload, this::class, eventValue, customAttributes)
    }

    override fun trackScreenView(screenName: String, screenClass: KClass<*>) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass.simpleName)
        }
        if (isPermissionAnalytics()) firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundle
        )
    }

    override fun setUserProperty(name: String, value: String?) {
        if (isPermissionAnalytics()) firebaseAnalytics.setUserProperty(name, value)
    }

    override fun setUserId(userId: String) {
        if (isPermissionAnalytics()) firebaseAnalytics.setUserId(userId)
    }

    private fun convertEventToFirebaseEvent(event: AnalyticsService.Events): String {
        return when (event) {
            AnalyticsService.Events.APP_OPEN -> FirebaseAnalytics.Event.APP_OPEN
            AnalyticsService.Events.LOGIN -> FirebaseAnalytics.Event.LOGIN
            AnalyticsService.Events.SEARCH -> FirebaseAnalytics.Event.SEARCH
            AnalyticsService.Events.SELECT_ITEM -> FirebaseAnalytics.Event.SELECT_ITEM
            else -> event.name.lowercase()
        }
    }
}
