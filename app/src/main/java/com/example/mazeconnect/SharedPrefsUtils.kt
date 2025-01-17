package com.example.mazeconnect

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsUtils {
    private const val PREFS_NAME = "MazeConnectPrefs"
    private const val ROLE_KEY = "user_role"

    fun saveRole(context: Context, role: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(ROLE_KEY, role).apply()
    }

    fun getRole(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(ROLE_KEY, null)
    }
}
