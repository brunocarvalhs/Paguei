package br.com.brunocarvalhs.paguei

import androidx.multidex.MultiDexApplication
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PagueiApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        dynamicColor()
    }

    private fun dynamicColor(toogle: Boolean = true) {
        if (DynamicColors.isDynamicColorAvailable() && toogle) {
            DynamicColors.applyToActivitiesIfAvailable(this)
        }
    }
}
