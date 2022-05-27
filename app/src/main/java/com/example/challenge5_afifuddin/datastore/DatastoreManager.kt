package com.example.challenge5_afifuddin.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.challenge5_afifuddin.model.User
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

        const val DEF_ID = -1
        const val DEF_NAMA = "default_nama"
        const val DEF_EMAIL = "default_email@gmail.com"
        const val DEF_USERNAME = "default_username"
        const val DEF_PASSWORD = "default_password"
        const val DEF_IMAGE = "no_image"
    }

    suspend fun saveUser(userData: User) {
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
                it[ID] ?: DEF_ID,
                it[USERNAME] ?: DEF_USERNAME,
                it[EMAIL] ?: DEF_EMAIL,
                it[PASSWORD] ?: DEF_PASSWORD,
                it[NAMA] ?: DEF_NAMA,
                 it[IMAGE_KEY] ?: DEF_IMAGE
            )
        }
    }
    suspend fun delete(){
        context.datastore.edit {
            it.clear()
        }
    }
}