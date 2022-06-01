package com.example.amistadzadanie.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amistadzadanie.data.model.Route
import com.example.amistadzadanie.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(private val repository: Repository): ViewModel() {

    private val mutableLiveData = MutableLiveData<Route>()
    val routeLiveData: LiveData<Route> = mutableLiveData

    fun loadRouteById(routeId: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
           val route = repository.getRouteById(routeId)
            mutableLiveData.postValue(route!!)
        } catch (e: Exception){
            Log.e("TAG", e.message.toString())
        }
    }


}