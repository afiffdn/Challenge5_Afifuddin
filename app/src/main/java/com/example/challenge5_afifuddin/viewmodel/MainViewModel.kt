package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.*
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_top_rated.GetTopRated
import com.example.challenge5_afifuddin.service.Repository
import com.example.challenge5_afifuddin.service.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _data = MutableLiveData<Resource<GetAllMovieNowShowing>>()
    val data: LiveData<Resource<GetAllMovieNowShowing>>
        get() = _data

    private val _datatop = MutableLiveData<Resource<GetTopRated>>()
    val datatop: LiveData<Resource<GetTopRated>>
        get() = _datatop

    private val _userPref = MutableLiveData<User>()
    val userPref :LiveData<User> get() =_userPref

    fun getAllMovieNowShowing() {
        viewModelScope.launch {
            _data.postValue(Resource.loading())
            try {
                _data.postValue(Resource.success(repository.getAllMovieNowShowing()))
            } catch (exeption: Exception) {
                _data.postValue(Resource.error(exeption.message ?: "Error"))
            }
        }
    }

    fun getTopRated() {
        viewModelScope.launch {
            _datatop.postValue(Resource.loading())
            try {
                _datatop.postValue(Resource.success(repository.getTopRated()))
            } catch (exception: Exception) {
                _datatop.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }

    fun getUserPref(){
        viewModelScope.launch {
            repository.getUserPref().collect(){
            _userPref.postValue(it)
            }
        }
    }

}