package com.kelas.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.kelas.todoapp.data.TodoDatabase
import com.kelas.todoapp.data.TodoDao
import com.kelas.todoapp.model.Todo
import java.util.Date

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao: TodoDao
    val todoList: LiveData<List<Todo>>

    init {
        val database = TodoDatabase.getDatabase(application)
        todoDao = database.todoDao()
        todoList = todoDao.getAllTodos()
    }

    fun addTodo(title: String) {
        if (title.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                val newTodo = Todo(title = title, createdAt = Date())
                todoDao.insert(newTodo)
            }
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.delete(id)
        }
    }
}