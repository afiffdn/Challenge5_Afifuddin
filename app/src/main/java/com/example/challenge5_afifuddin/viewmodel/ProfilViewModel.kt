package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.service.Repository
import kotlinx.coroutines.launch

class ProfilViewModel(private val repository: Repository) : ViewModel() {
    private val _userDataPref = MutableLiveData<User>()
    val userDataPref: LiveData<User> get() = _userDataPref
    fun getDataPref() {
        viewModelScope.launch {
            repository.getUserPref().collect() {
                _userDataPref.postValue(it)
            }
        }
    }

    fun updataUser(user: User) {
        viewModelScope.launch {
            repository.update(user)
        }
    }

    fun updateUserPref(user: User) {
        viewModelScope.launch {
            repository.saveUserPref(user)
        }
    }

    fun setUserPref(user: User) {
        viewModelScope.launch {
            repository.saveUserPref(user)
        }
    }

    fun deleteUserPref() {
        viewModelScope.launch {
            repository.deleteUserPref()
        }
    }
}