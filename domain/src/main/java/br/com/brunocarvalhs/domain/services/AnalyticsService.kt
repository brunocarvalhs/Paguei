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
        SCREEN_VIEWED,
        APP_OPEN,
        LOGIN,
        LOGOUT,
        SAVE_SUCCESS,
        SAVE_FAILURE,
        COST_ITEM_CLICKED,
        ADD_COST_BUTTON_CLICKED,
        COST_ITEM_LONG_CLICKED,
        CALCULATION_MENU_SELECTED,
        EXTRACTS_MENU_SELECTED,
        REPORT_SCREEN_VIEWED,
        BUTTON_CLICKED,
        ICON_CLICKED,
        SELECT_ITEM,
        CLICK_EVENT,
        MEMBER_LONG_CLICKED,
        SEARCH_CLOSED,
        SEARCH,
        COPY_EVENT,
    }
}