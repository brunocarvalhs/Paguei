package br.com.brunocarvalhs.data.services

import android.content.SharedPreferences
import br.com.brunocarvalhs.domain.services.DataStore
import javax.inject.Inject

class DataStoreService @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : DataStore {

    companion object {
        const val PREFERENCE_DATA_STORE = "PREFERENCE_DATA_STORE"
    }

    override fun put(key: String, value: Any) {
        sharedPreferences.edit().apply {
            when (value) {
                is Boolean -> this.putBoolean(key, value)
                is Float -> this.putFloat(key, value)
                is Int -> this.putInt(key, value)
                is Long -> this.putLong(key, value)
                is String -> this.putString(key, value)
                else -> this.putString(key, value.toString())
            }
        }

    }

    override fun <T : Any> get(key: String, defValue: T): T {
        return when (defValue) {
            is Boolean -> sharedPreferences.getBoolean(key, defValue)
            is Float -> sharedPreferences.getFloat(key, defValue)
            is Int -> sharedPreferences.getInt(key, defValue)
            is Long -> sharedPreferences.getLong(key, defValue)
            is String -> sharedPreferences.getString(key, defValue)
            else -> sharedPreferences.getString(key, defValue.toString())
        } as T
    }
}