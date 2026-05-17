package com.ajet.template.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

/**
 * 主题模式枚举
 */
enum class AppThemeMode(val value: Int) {
    LIGHT(0),
    DARK(1),
    AMOLED(2);
    
    companion object {
        fun fromValue(value: Int): AppThemeMode {
            return values().find { it.value == value } ?: LIGHT
        }
    }
}

/**
 * 主题管理器 - 用于持久化存储主题偏好
 */
object ThemeManager {
    private val THEME_KEY = intPreferencesKey("theme_mode")
    
    /**
     * 获取当前主题模式（异步 Flow）
     */
    fun getThemeMode(context: Context): Flow<AppThemeMode> {
        return context.dataStore.data.map { preferences ->
            val value = preferences[THEME_KEY] ?: AppThemeMode.LIGHT.value
            AppThemeMode.fromValue(value)
        }
    }
    
    /**
     * 同步获取当前主题模式（用于启动时快速加载）
     */
    fun getThemeModeSync(context: Context): AppThemeMode {
        return runBlocking {
            getThemeMode(context).first()
        }
    }
    
    /**
     * 保存主题模式
     */
    suspend fun saveThemeMode(context: Context, mode: AppThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = mode.value
        }
    }
    
    /**
     * 切换到下一个主题模式（循环：LIGHT -> DARK -> AMOLED -> LIGHT）
     */
    fun getNextThemeMode(currentMode: AppThemeMode): AppThemeMode {
        return when (currentMode) {
            AppThemeMode.LIGHT -> AppThemeMode.DARK
            AppThemeMode.DARK -> AppThemeMode.AMOLED
            AppThemeMode.AMOLED -> AppThemeMode.LIGHT
        }
    }
    
    /**
     * 判断是否为暗色主题
     */
    fun isDarkTheme(mode: AppThemeMode): Boolean {
        return mode == AppThemeMode.DARK || mode == AppThemeMode.AMOLED
    }
    
    /**
     * 判断是否为纯黑主题
     */
    fun isAmoledTheme(mode: AppThemeMode): Boolean {
        return mode == AppThemeMode.AMOLED
    }
}
