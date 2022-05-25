package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.*
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.service.Repository
import com.example.challenge5_afifuddin.service.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _data = MutableLiveData<Resource<List<GetAllMovieNowShowing>>>()
    val data: LiveData<Resource<List<GetAllMovieNowShowing>>>
        get() = _data

    fun getAllMovieNowShowing() {
        viewModelScope.launch {
            _data.postValue(Resource.loading())
            try {
                _data.postValue(Resource.success(repository.getAllMovieNowShowing()))
            } catch (exeption:Exception){
                _data.postValue(Resource.error(exeption.message ?: "Error"))
            }
        }
    }
}