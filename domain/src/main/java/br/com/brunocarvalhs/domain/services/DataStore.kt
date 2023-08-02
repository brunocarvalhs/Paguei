package br.com.brunocarvalhs.domain.services

interface DataStore {
    fun put(key: String, value: Any)
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun getFloat(key: String, defValue: Float): Float
    fun getInt(key: String, defValue: Int): Int
    fun getLong(key: String, defValue: Long): Long
    fun getString(key: String, defValue: String): String?
}