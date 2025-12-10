package com.kelas.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.kelas.todoapp.ui.pages.TodoListPage
import com.kelas.todoapp.ui.theme.TodoAppTheme
import com.kelas.todoapp.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            TodoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _: PaddingValues ->
                    val todoViewModel: TodoViewModel = viewModel(
                        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                    )
                    TodoListPage(viewModel = todoViewModel)
                }
            }
        }
    }
}