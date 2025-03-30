package com.example.app_comedor.data.pref

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SessionPref {

    private const val PREFERENCES_FILE_KEY = "com.example.settings"
    private const val IS_SESSION_KEY = "IS_SESSION"
    private val _isSession = MutableStateFlow(false)

    fun setSesion(context: Context, isSession: Boolean) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(IS_SESSION_KEY, isSession)
            apply()
        }
        _isSession.value = isSession
    }

    fun isNotSessionFlow(context: Context): Flow<Boolean> {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        _isSession.value = sharedPref.getBoolean(IS_SESSION_KEY, false)
        return _isSession.asStateFlow()
    }
    fun isNotSession(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        _isSession.value = sharedPref.getBoolean(IS_SESSION_KEY, false)
        return _isSession.value
    }
}