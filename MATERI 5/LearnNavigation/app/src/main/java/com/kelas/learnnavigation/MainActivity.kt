package com.kelas.learnnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kelas.learnnavigation.ui.theme.LearnNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnNavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    MyAppNavigation()
                }
            }
        }
    }
}