package com.example.yourdigitalpath

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleManager {

    /**
     * يُطبَّق عند بدء التطبيق (Application.onCreate) وعند تغيير المستخدم للغة.
     * يضمن أن العربية هي الافتراضية لو مفيش لغة محفوظة.
     */
    fun setLocale(context: Context, language: String = "ar") {
        // 1) حدّث AppCompatDelegate (بيتذكرها الـ framework تلقائياً من Android 13+)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(language)
        )

        // 2) حدّث الـ Configuration للـ Context الحالي (مهم لـ API < 33)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    /** تحفظ اختيار المستخدم في SharedPreferences ثم تطبّق التغيير */
    fun changeLanguage(context: Context, language: String) {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("app_language", language)
            .apply()
        setLocale(context, language)
    }

    /** تُرجع اللغة المحفوظة (افتراضي "ar") */
    fun getSavedLanguage(context: Context): String =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getString("app_language", "ar") ?: "ar"
}
