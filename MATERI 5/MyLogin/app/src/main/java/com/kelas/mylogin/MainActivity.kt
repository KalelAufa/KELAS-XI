package com.kelas.mylogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelas.mylogin.ui.theme.MyLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyLoginTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile Image",
            modifier = Modifier.size(200.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Welcome Back",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Login to your account",
            fontSize = 16.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Input Fields Section
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Login Button
        Button(
            onClick = { /* TODO: Implement login logic */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("LOGIN")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Forgot Password
        TextButton(
            onClick = { /* TODO: Implement forgot password */ }
        ) {
            Text("Forgot Password?")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Social Login Section
        Text(
            text = "Sign in with",
            fontSize = 14.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Facebook Icon
            Image(
                painter = painterResource(id = R.drawable.ic_facebook),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { /* TODO: Implement Facebook login */ }
            )
            
            // Google Icon
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { /* TODO: Implement Google login */ }
            )
            
            // Twitter Icon
            Image(
                painter = painterResource(id = R.drawable.ic_twitter),
                contentDescription = "Twitter",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { /* TODO: Implement Twitter login */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MyLoginTheme {
        LoginScreen()
    }
}