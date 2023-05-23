package br.com.brunocarvalhs.paguei

import androidx.multidex.MultiDexApplication
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PagueiApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
//        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
