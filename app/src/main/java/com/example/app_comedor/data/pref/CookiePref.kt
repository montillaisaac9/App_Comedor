package com.example.app_comedor.data.pref

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CookiesPref {
    private const val PREFERENCES_FILE_KEY = "com.example.settings"
    private val _instance = MutableStateFlow<String?>(null)
    private const val INSTANCE = "cookies_key"

    /**
     * [context] fundamental object that provides information about the application environment necessary to access the getSharedPreferences
     * [instance] value used as instance in app
     */
    fun setInstancesPref(context: Context, instance: String) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(INSTANCE, instance)
            apply()
        }
        _instance.value = instance
    }

    /**
     *  [context] fundamental object that provides information about the application environment necessary to access the getSharedPreferences
     */
    fun instanceValueFlow(context: Context): StateFlow<String?> {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        _instance.value = sharedPref.getString(INSTANCE, null)
        return _instance.asStateFlow()
    }
}
