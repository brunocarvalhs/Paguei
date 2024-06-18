package br.com.brunocarvalhs.paguei.features.splash.providers

import android.content.Context
import br.com.brunocarvalhs.data.services.AnalyticsServiceImpl
import br.com.brunocarvalhs.data.services.DataStoreService
import br.com.brunocarvalhs.splash.commons.providers.SplashAnalyticsProvider
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlin.reflect.KClass

class SplashAnalyticsProviderImpl(
    private val context: Context
) : SplashAnalyticsProvider {

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

    override fun setUserId(id: String) {
        analytics.setUserId(id)
    }

    override fun trackScreenView(activityName: String, kClass: KClass<*>) {
        analytics.trackScreenView(activityName, kClass)
    }
}