package com.kelas.wavesoffood.data.model.response

import com.kelas.wavesoffood.data.model.Restaurant

data class RestaurantResponse(
    val success: Boolean,
    val message: String,
    val data: RestaurantData
)

data class RestaurantData(
    val restaurants: List<Restaurant>,
    val totalCount: Int,
    val page: Int,
    val totalPages: Int
)
