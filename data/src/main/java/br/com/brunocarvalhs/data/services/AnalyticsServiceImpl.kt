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
            AnalyticsService.Events.AD_IMPRESSION -> FirebaseAnalytics.Event.AD_IMPRESSION
            AnalyticsService.Events.ADD_PAYMENT_INFO -> FirebaseAnalytics.Event.ADD_PAYMENT_INFO
            AnalyticsService.Events.ADD_TO_CART -> FirebaseAnalytics.Event.ADD_TO_CART
            AnalyticsService.Events.ADD_TO_WISHLIST -> FirebaseAnalytics.Event.ADD_TO_WISHLIST
            AnalyticsService.Events.APP_OPEN -> FirebaseAnalytics.Event.APP_OPEN
            AnalyticsService.Events.BEGIN_CHECKOUT -> FirebaseAnalytics.Event.BEGIN_CHECKOUT
            AnalyticsService.Events.CAMPAIGN_DETAILS -> FirebaseAnalytics.Event.CAMPAIGN_DETAILS
            AnalyticsService.Events.GENERATE_LEAD -> FirebaseAnalytics.Event.GENERATE_LEAD
            AnalyticsService.Events.JOIN_GROUP -> FirebaseAnalytics.Event.JOIN_GROUP
            AnalyticsService.Events.LEVEL_END -> FirebaseAnalytics.Event.LEVEL_END
            AnalyticsService.Events.LEVEL_START -> FirebaseAnalytics.Event.LEVEL_START
            AnalyticsService.Events.LEVEL_UP -> FirebaseAnalytics.Event.LEVEL_UP
            AnalyticsService.Events.LOGIN -> FirebaseAnalytics.Event.LOGIN
            AnalyticsService.Events.POST_SCORE -> FirebaseAnalytics.Event.POST_SCORE
            AnalyticsService.Events.SEARCH -> FirebaseAnalytics.Event.SEARCH
            AnalyticsService.Events.SELECT_CONTENT -> FirebaseAnalytics.Event.SELECT_CONTENT
            AnalyticsService.Events.SHARE -> FirebaseAnalytics.Event.SHARE
            AnalyticsService.Events.SIGN_UP -> FirebaseAnalytics.Event.SIGN_UP
            AnalyticsService.Events.SPEND_VIRTUAL_CURRENCY -> FirebaseAnalytics.Event.SPEND_VIRTUAL_CURRENCY
            AnalyticsService.Events.TUTORIAL_BEGIN -> FirebaseAnalytics.Event.TUTORIAL_BEGIN
            AnalyticsService.Events.TUTORIAL_COMPLETE -> FirebaseAnalytics.Event.TUTORIAL_COMPLETE
            AnalyticsService.Events.UNLOCK_ACHIEVEMENT -> FirebaseAnalytics.Event.UNLOCK_ACHIEVEMENT
            AnalyticsService.Events.VIEW_ITEM -> FirebaseAnalytics.Event.VIEW_ITEM
            AnalyticsService.Events.VIEW_ITEM_LIST -> FirebaseAnalytics.Event.VIEW_ITEM_LIST
            AnalyticsService.Events.VIEW_SEARCH_RESULTS -> FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS
            AnalyticsService.Events.EARN_VIRTUAL_CURRENCY -> FirebaseAnalytics.Event.EARN_VIRTUAL_CURRENCY
            AnalyticsService.Events.SCREEN_VIEW -> FirebaseAnalytics.Event.SCREEN_VIEW
            AnalyticsService.Events.REMOVE_FROM_CART -> FirebaseAnalytics.Event.REMOVE_FROM_CART
            AnalyticsService.Events.ADD_SHIPPING_INFO -> FirebaseAnalytics.Event.ADD_SHIPPING_INFO
            AnalyticsService.Events.PURCHASE -> FirebaseAnalytics.Event.PURCHASE
            AnalyticsService.Events.REFUND -> FirebaseAnalytics.Event.REFUND
            AnalyticsService.Events.SELECT_ITEM -> FirebaseAnalytics.Event.SELECT_ITEM
            AnalyticsService.Events.SELECT_PROMOTION -> FirebaseAnalytics.Event.SELECT_PROMOTION
            AnalyticsService.Events.VIEW_CART -> FirebaseAnalytics.Event.VIEW_CART
            AnalyticsService.Events.VIEW_PROMOTION -> FirebaseAnalytics.Event.VIEW_PROMOTION
        }
    }
}
