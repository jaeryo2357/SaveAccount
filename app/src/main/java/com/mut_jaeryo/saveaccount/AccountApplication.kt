package com.mut_jaeryo.saveaccount

import android.app.Application
import androidx.preference.PreferenceManager
import com.mut_jaeryo.saveaccount.theme.ThemeHelper
import com.mut_jaeryo.saveaccount.theme.ThemeHelper.applyTheme
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AccountApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE)

        applyTheme(themePref ?: ThemeHelper.DEFAULT_MODE)
    }
}