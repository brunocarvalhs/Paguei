package br.com.brunocarvalhs.domain.services

interface AdsService {
    fun start()

    fun <T> banner(view: T, idBanner: String? = null)

    fun initFullBanner(idBanner: String? = null)

    fun <T> fullBanner(view: T)
    fun <T> fullBanner(view: T, listeners: FullBannerListeners)

    interface FullBannerListeners {
        fun onClicked()
        fun onDismissed()
        fun onFailed(error: String)
        fun onImpression()
        fun onShowed()
    }
}