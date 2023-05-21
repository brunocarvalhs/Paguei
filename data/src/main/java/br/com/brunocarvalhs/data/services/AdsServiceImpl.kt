package br.com.brunocarvalhs.data.services

import android.content.Context
import android.view.ViewGroup
import br.com.brunocarvalhs.domain.services.AdsService
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdsServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AdsService {

    override fun start() {
        MobileAds.initialize(context) {}
    }

    override fun <T> banner(view: T, idBanner: String?) {
        if (view is ViewGroup) {
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = idBanner ?: "ca-app-pub-3940256099942544/6300978111"
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            view.addView(adView)
        }
    }
}