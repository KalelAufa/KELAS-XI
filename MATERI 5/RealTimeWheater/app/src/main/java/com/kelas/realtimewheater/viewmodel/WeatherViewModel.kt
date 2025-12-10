package com.kelas.realtimewheater.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelas.realtimewheater.api.NetworkResponse
import com.kelas.realtimewheater.api.RetrofitInstance
import com.kelas.realtimewheater.api.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    
    // API Key dari weatherapi.com
    private val API_KEY = "c32da568ed804ac1a55170636250910"
    
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult
    
    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.weatherApi.getWeather(API_KEY, city)
                
                if (response.isSuccessful && response.body() != null) {
                    _weatherResult.postValue(NetworkResponse.Success(response.body()!!))
                } else {
                    _weatherResult.postValue(NetworkResponse.Error("Failed to load data"))
                }
            } catch (e: Exception) {
                _weatherResult.postValue(NetworkResponse.Error("Error: ${e.message}"))
                Log.e("WeatherViewModel", "Error fetching weather", e)
            }
        }
    }
}
