package br.com.brunocarvalhs.domain.services

interface DataStore {
    fun put(key: String, value: Any)
    fun <T : Any> get(key: String, defValue: T): T
}