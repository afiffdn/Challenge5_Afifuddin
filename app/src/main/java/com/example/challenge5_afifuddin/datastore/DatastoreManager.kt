package com.example.challenge5_afifuddin.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class DatastoreManager(private val context: Context) {

    companion object {
        private val DATASTORELOGIN = "login_preferences"
        private val EMAIL = stringPreferencesKey("email")
        private val USERNAME = stringPreferencesKey("username")
        private val Context.datastore by preferencesDataStore(name = DATASTORELOGIN)
    }

    suspend fun save(userData: UserDatastore) {
        context.datastore.edit {
            it[EMAIL] = userData.email
            it[USERNAME] = userData.username
        }
    }
}