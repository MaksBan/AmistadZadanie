package com.example.amistadzadanie.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amistadzadanie.Resource
import com.example.amistadzadanie.data.model.Route
import com.example.amistadzadanie.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {


    private val _mutableState = MutableLiveData<Resource<List<Route>>>()
    val mutableState: LiveData<Resource<List<Route>>> = _mutableState

    fun loadRoutes() = viewModelScope.launch(Dispatchers.IO) {
        _mutableState.postValue(Resource.Loading())
        try{
            val response = Resource.Success(repository.getRoutes())
            _mutableState.postValue(response)
        } catch(e: Exception) {
            _mutableState.postValue(Resource.Error(e.message.toString()))

        }

        }

    }
