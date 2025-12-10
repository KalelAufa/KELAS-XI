package com.kelas.wavesoffood.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelas.wavesoffood.data.model.*

class Converters {
    
    private val gson = Gson()
    
    // List<String> converters
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    // List<Address> converters
    @TypeConverter
    fun fromAddressList(value: List<Address>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toAddressList(value: String): List<Address> {
        val listType = object : TypeToken<List<Address>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    // List<FoodSize> converters
    @TypeConverter
    fun fromFoodSizeList(value: List<FoodSize>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toFoodSizeList(value: String): List<FoodSize> {
        val listType = object : TypeToken<List<FoodSize>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    // List<AddOn> converters
    @TypeConverter
    fun fromAddOnList(value: List<AddOn>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toAddOnList(value: String): List<AddOn> {
        val listType = object : TypeToken<List<AddOn>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    // OpeningHours converter
    @TypeConverter
    fun fromOpeningHours(value: OpeningHours?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toOpeningHours(value: String?): OpeningHours? {
        return value?.let { gson.fromJson(it, OpeningHours::class.java) }
    }
    
    // FoodSize converter
    @TypeConverter
    fun fromFoodSize(value: FoodSize?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toFoodSize(value: String?): FoodSize? {
        return value?.let { gson.fromJson(it, FoodSize::class.java) }
    }
    
    // Address converter
    @TypeConverter
    fun fromAddress(value: Address): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toAddress(value: String): Address {
        return gson.fromJson(value, Address::class.java)
    }
    
    // PaymentMethod converter
    @TypeConverter
    fun fromPaymentMethod(value: PaymentMethod): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toPaymentMethod(value: String): PaymentMethod {
        return gson.fromJson(value, PaymentMethod::class.java)
    }
    
    // List<OrderItem> converters
    @TypeConverter
    fun fromOrderItemList(value: List<OrderItem>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toOrderItemList(value: String): List<OrderItem> {
        val listType = object : TypeToken<List<OrderItem>>() {}.type
        return gson.fromJson(value, listType)
    }
}
