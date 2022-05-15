package com.example.challenge5_afifuddin.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.challenge5_afifuddin.login.database_login.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatastoreManager(private val context: Context) {

    companion object {
        private val DATASTORELOGIN = "login_preferences"
        private val ID = intPreferencesKey("id_key")
        private val USERNAME = stringPreferencesKey("username_key")
        private val EMAIL = stringPreferencesKey("email_key")
        private val PASSWORD = stringPreferencesKey("password_key")
        private val NAMA = stringPreferencesKey("nama_key")
        private val IMAGE_KEY = stringPreferencesKey("image_key")
        private val Context.datastore by preferencesDataStore(name = DATASTORELOGIN)
    }

    suspend fun save(userData: User) {
        context.datastore.edit {
            it[ID] = userData.id_user!!.toInt()
            it[USERNAME] = userData.username.toString()
            it[EMAIL] = userData.email.toString()
            it[PASSWORD] = userData.password.toString()
            it[NAMA] = userData.namaLengkap.toString()
            it[IMAGE_KEY] = userData.image
        }
    }

    fun getUser(): Flow<User> {
        return context.datastore.data.map {
            User(
                it[ID] ?: -1,
                it[USERNAME] ?: "default_username",
                it[EMAIL] ?: "default_email",
                it[PASSWORD] ?: "default_password",
                it[NAMA] ?: "default_nama",
                 it[IMAGE_KEY] ?: "no_image"
            )
        }
    }
    suspend fun delete(){
        context.datastore.edit {
            it.clear()
        }
    }
}