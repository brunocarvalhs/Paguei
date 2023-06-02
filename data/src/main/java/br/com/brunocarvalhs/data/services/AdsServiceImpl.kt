package br.com.brunocarvalhs.data.services

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import br.com.brunocarvalhs.domain.services.AdsService
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdsServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AdsService {

    companion object {
        private var mInterstitialAd: InterstitialAd? = null
    }

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

    override fun initFullBanner(idBanner: String?) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context,
            idBanner ?: "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    override fun <T> fullBanner(view: T) {
        if (view is Activity) {
            mInterstitialAd?.show(view)
            mInterstitialAd = null
        }
    }

    override fun <T> fullBanner(view: T, listeners: AdsService.FullBannerListeners) {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                listeners.onClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null
                listeners.onDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                mInterstitialAd = null
                listeners.onFailed(adError.message)
            }

            override fun onAdImpression() {
                listeners.onImpression()
            }

            override fun onAdShowedFullScreenContent() {
                listeners.onShowed()
            }
        }

        if (view is Activity) {
            mInterstitialAd?.show(view)
            mInterstitialAd = null
        }
    }
}