package br.com.brunocarvalhs.data.services

import android.content.SharedPreferences
import br.com.brunocarvalhs.domain.services.DataStore
import javax.inject.Inject

class DataStoreService @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : DataStore {

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

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return sharedPreferences.getFloat(key, defValue)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    override fun getString(key: String, defValue: String): String? {
        return sharedPreferences.getString(key, defValue)
    }
}