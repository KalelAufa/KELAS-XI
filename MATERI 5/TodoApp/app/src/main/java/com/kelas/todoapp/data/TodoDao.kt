package com.kelas.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kelas.todoapp.model.Todo

@Dao
interface TodoDao {
    
    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getAllTodos(): LiveData<List<Todo>>
    
    @Insert
    suspend fun insert(todo: Todo)
    
    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun delete(id: Long)
}