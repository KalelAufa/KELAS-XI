package com.kelas.realtimewheater.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.weatherapi.com/"
    
    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    val weatherApi: WeatherAPI by lazy {
        getInstance().create(WeatherAPI::class.java)
    }
}
