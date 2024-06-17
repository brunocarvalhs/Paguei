package br.com.brunocarvalhs.paguei.features.providers

import android.content.Context
import br.com.brunocarvalhs.auth.commons.providers.AuthAnalyticsProvider
import br.com.brunocarvalhs.data.services.AnalyticsServiceImpl
import br.com.brunocarvalhs.data.services.DataStoreService
import br.com.brunocarvalhs.domain.services.AnalyticsService
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlin.reflect.KClass

class AuthAnalyticsProviderImpl(
    private val context: Context
) : AuthAnalyticsProvider {

    private val analytics by lazy {
        AnalyticsServiceImpl(
            firebaseAnalytics = Firebase.analytics,
            dataStoreService = DataStoreService(
                context.getSharedPreferences(
                    context.applicationInfo.packageName,
                    Context.MODE_PRIVATE
                )
            )
        )
    }

    override fun trackEvent(
        eventName: AnalyticsService.Events,
        eventPayload: Map<String?, Any?>,
        eventPage: KClass<*>,
        customAttributes: Map<String, String>?
    ) {
        analytics.trackEvent(
            eventName = eventName.name,
            eventPayload = eventPayload,
            eventPage = eventPage,
            customAttributes = customAttributes,
            eventValue = null
        )
    }

    override fun setUserId(id: String) {
        analytics.setUserId(id)
    }

    override fun trackScreenView(activityName: String, kClass: KClass<*>) {
        analytics.trackScreenView(activityName, kClass)
    }
}