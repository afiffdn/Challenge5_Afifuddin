package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.service.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    private val _userdata = MutableLiveData<User>()
    val user : LiveData<User> get() = _userdata
    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }
}