package com.example.yourdigitalpath.presentation.Login.component

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

data class SavedAccount(
    val email: String,
    val name: String,
    val initials: String
)

@Singleton
class SavedAccountsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("saved_accounts", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getAccounts(): List<SavedAccount> {
        val json = prefs.getString("accounts", null) ?: return emptyList()
        val type = object : TypeToken<List<SavedAccount>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveAccount(email: String, name: String) {
        val accounts = getAccounts().toMutableList()
        val initials = name.split(" ").take(2).mapNotNull { it.firstOrNull()?.toString() }.joinToString("")
        val existing = accounts.indexOfFirst { it.email == email }
        if (existing != -1) accounts.removeAt(existing)
        accounts.add(0, SavedAccount(email = email, name = name, initials = initials))
        val trimmed = accounts.take(5)
        prefs.edit().putString("accounts", gson.toJson(trimmed)).apply()
    }

    fun removeAccount(email: String) {
        val accounts = getAccounts().toMutableList()
        accounts.removeAll { it.email == email }
        prefs.edit().putString("accounts", gson.toJson(accounts)).apply()
    }

    fun clearAll() {
        prefs.edit().remove("accounts").apply()
    }
}