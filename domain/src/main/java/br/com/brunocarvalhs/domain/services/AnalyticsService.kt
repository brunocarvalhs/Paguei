package br.com.brunocarvalhs.domain.services

import kotlin.reflect.KClass

interface AnalyticsService {
    fun trackEvent(
        eventName: Events,
        eventPayload: Map<String?, Any?>,
        eventPage: KClass<*>,
        eventValue: Double? = null,
        customAttributes: Map<String, String>? = null
    )

    fun trackEvent(
        eventName: String,
        eventPayload: Map<String?, Any?>,
        eventPage: KClass<*>,
        eventValue: Double? = null,
        customAttributes: Map<String, String>? = null
    )

    fun trackUserEvent(
        userId: String,
        eventAction: String,
        eventValue: Double? = null,
        customAttributes: Map<String, String>? = null
    )

    fun trackScreenView(screenName: String, screenClass: KClass<*>)
    fun setUserProperty(name: String, value: String? = null)
    fun setUserId(userId: String)

    enum class Events {
        AD_IMPRESSION,
        ADD_PAYMENT_INFO,
        ADD_TO_CART,
        ADD_TO_WISHLIST,
        APP_OPEN,
        BEGIN_CHECKOUT,
        CAMPAIGN_DETAILS,
        GENERATE_LEAD,
        JOIN_GROUP,
        LEVEL_END,
        LEVEL_START,
        LEVEL_UP,
        LOGIN,
        POST_SCORE,
        SEARCH,
        SELECT_CONTENT,
        SHARE,
        SIGN_UP,
        SPEND_VIRTUAL_CURRENCY,
        TUTORIAL_BEGIN,
        TUTORIAL_COMPLETE,
        UNLOCK_ACHIEVEMENT,
        VIEW_ITEM,
        VIEW_ITEM_LIST,
        VIEW_SEARCH_RESULTS,
        EARN_VIRTUAL_CURRENCY,
        SCREEN_VIEW,
        REMOVE_FROM_CART,
        ADD_SHIPPING_INFO,
        PURCHASE,
        REFUND,
        SELECT_ITEM,
        SELECT_PROMOTION,
        VIEW_CART,
        VIEW_PROMOTION,
    }
}