package com.kelas.todoapp.data

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun timestampToDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}