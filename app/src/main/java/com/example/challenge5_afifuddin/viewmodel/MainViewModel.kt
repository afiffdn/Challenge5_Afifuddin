package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.model.User

class MainViewModel(private val datastore:DatastoreManager) : ViewModel() {

    suspend fun setDataUser(user : User){
        datastore.save(user)
    }

    fun getData() : LiveData<User>{
    return datastore.getUser().asLiveData()
    }
}