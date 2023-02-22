package br.com.brunocarvalhs.domain.services

interface DataStore {
    fun putString(key: String, value: String?)
    fun getString(key: String, defValue: String?): String?
}