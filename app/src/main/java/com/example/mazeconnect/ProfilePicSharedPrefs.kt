package com.example.mazeconnect

import android.content.Context
import android.content.SharedPreferences

object ProfilePicSharedPrefs {
    private const val PREFS_NAME = "UserProfilePicPrefs"
    private const val PROFILE_PIC_URI_KEY = "user_profile_pic_uri"

    fun saveProfilePicUri(context: Context, uri: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(PROFILE_PIC_URI_KEY, uri).apply()
    }

    fun getProfilePicUri(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PROFILE_PIC_URI_KEY, null)
    }
}
