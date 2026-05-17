package com.ajet.template.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

object LocaleHelper {
    
    private const val PREFS_NAME = "ajet_template_prefs"
    private const val LANGUAGE_KEY = "language"
    
    // 支持的语言
    enum class Language(val code: String, val displayName: String) {
        CHINESE("zh", "中文"),
        ENGLISH("en", "English")
    }
    
    /**
     * 设置应用语言
     */
    fun setLocale(context: Context, language: Language): Context {
        saveLanguage(context, language)
        return updateResources(context, language.code)
    }
    
    /**
     * 获取当前保存的语言
     */
    fun getLanguage(context: Context): Language {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val code = prefs.getString(LANGUAGE_KEY, "zh") ?: "zh"
        return Language.values().find { it.code == code } ?: Language.CHINESE
    }
    
    /**
     * 保存语言设置
     */
    private fun saveLanguage(context: Context, language: Language) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_KEY, language.code).apply()
    }
    
    /**
     * 更新资源
     */
    private fun updateResources(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            return context.createConfigurationContext(configuration)
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
            @Suppress("DEPRECATION")
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return context
        }
    }
    
    /**
     * 切换语言(在中英文之间切换)
     */
    fun toggleLanguage(context: Context): Language {
        val current = getLanguage(context)
        val newLanguage = if (current == Language.CHINESE) Language.ENGLISH else Language.CHINESE
        setLocale(context, newLanguage)
        return newLanguage
    }
}
