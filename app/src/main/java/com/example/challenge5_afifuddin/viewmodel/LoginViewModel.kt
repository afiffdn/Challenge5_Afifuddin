package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.service.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) :ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user

    private val _userPref : MutableLiveData<User> = MutableLiveData()
    val userPref : LiveData<User> get() = _userPref

    fun checkUser(email:String, password:String){
        viewModelScope.launch {
            _user.postValue(repository.check(email,password))
        }
    }

    fun saveUserPref(userPref :User){
        viewModelScope.launch {
            repository.saveUserPref(userPref)
        }
    }

    fun getUserPref(){
        viewModelScope.launch{
            repository.getUserPref().collect(){
            _userPref.postValue(it)
            }
        }
    }
}