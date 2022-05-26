package com.example.challenge5_afifuddin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import java.lang.IllegalArgumentException
//
//class ViewModelFactory(private val datastore: DatastoreManager) :
//    ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        when {
//            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
//                return MainViewModel(datastore) as T
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
//        }
//    }
//}