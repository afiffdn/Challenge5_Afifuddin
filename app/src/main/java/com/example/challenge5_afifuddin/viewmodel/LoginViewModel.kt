package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.service.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) :ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user

    fun checkUser(email:String, password:String){
        viewModelScope.launch {
            _user.postValue(repository.check(email,password))
        }
    }
}