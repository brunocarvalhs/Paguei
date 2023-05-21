package br.com.brunocarvalhs.domain.services

interface AdsService {
    fun start()

    fun <T> banner(view: T, idBanner: String? = null)
}