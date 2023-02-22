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

    override fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value)
    }

    override fun getString(key: String, defValue: String?): String? {
        return sharedPreferences.getString(key, defValue)
    }
}