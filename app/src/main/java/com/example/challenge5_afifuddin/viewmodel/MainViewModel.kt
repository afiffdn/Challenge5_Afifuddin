package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge5_afifuddin.login.database_login.User

class MainViewModel : ViewModel() {
    val dataUser:MutableLiveData<User> = MutableLiveData()

    fun  getUser (user:User){
        dataUser.postValue(user)
    }
}