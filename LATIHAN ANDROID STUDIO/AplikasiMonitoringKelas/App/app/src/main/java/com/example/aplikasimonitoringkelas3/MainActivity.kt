package com.example.aplikasimonitoringkelas3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import android.util.Patterns
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasimonitoringkelas3.data.repository.UserRepository
import com.example.aplikasimonitoringkelas3.ui.theme.AplikasiMonitoringKelas3Theme
import com.example.aplikasimonitoringkelas3.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sin

private val EaseInOutSine = CubicBezierEasing(0.37f, 0f, 0.63f, 1f)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplikasiMonitoringKelas3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoadingDotsAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 200)
                ),
                label = "dot$index"
            )
            
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 200)
                ),
                label = "dotScale$index"
            )
            
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .scale(scale)
                    .alpha(alpha)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userRepository = remember { UserRepository() }
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }
    var loginData by remember { mutableStateOf<com.example.aplikasimonitoringkelas3.data.model.LoginData?>(null) }
    
    // Animation states
    var showContent by remember { mutableStateOf(false) }
    var shakeError by remember { mutableStateOf(false) }
    
    val isEmailValid = remember(email) { Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    
    // Helper function to get activity based on role
    fun getActivityForRole(role: String): Intent {
        return when (role.lowercase()) {
            "siswa" -> Intent(context, SiswaActivity::class.java)
            "kurikulum" -> Intent(context, KurikulumActivity::class.java)
            "kepala-sekolah" -> Intent(context, KepalaSekolahActivity::class.java)
            "admin" -> Intent(context, AdminActivity::class.java)
            else -> Intent(context, SiswaActivity::class.java)
        }
    }
    
    // Trigger content animation
    LaunchedEffect(Unit) {
        delay(300)
        showContent = true
    }
    
    // Logo animation
    val infiniteTransition = rememberInfiniteTransition(label = "logo")
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoScale"
    )
    
    val logoGlow by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoGlow"
    )
    
    // Shake animation for error
    val shakeOffset by animateFloatAsState(
        targetValue = if (shakeError) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "shake",
        finishedListener = { shakeError = false }
    )
    
    // Button press animation
    var buttonPressed by remember { mutableStateOf(false) }
    val buttonScale by animateFloatAsState(
        targetValue = if (buttonPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "buttonScale"
    )
    
    // Success animation handler
    LaunchedEffect(showSuccess) {
        if (showSuccess && loginData != null) {
            delay(1500)
            val intent = getActivityForRole(loginData!!.user.role)
            intent.putExtra("USER_NAME", loginData?.user?.nama)
            intent.putExtra("USER_EMAIL", loginData?.user?.email)
            intent.putExtra("USER_ROLE", loginData?.user?.role)
            context.startActivity(intent)
        }
    }
    
    // Background with gradient
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A0A1F),
                        BackgroundDark,
                        Color(0xFF000000)
                    )
                )
            )
    ) {
        // Animated decorative circles
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-100).dp, y = (-100).dp)
                .alpha(0.15f)
                .scale(logoScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Primary, Primary.copy(alpha = 0.3f))
                    )
                )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = 50.dp)
                .alpha(0.15f)
                .scale(logoScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Secondary, Secondary.copy(alpha = 0.3f))
                    )
                )
        )
        
        // Success overlay
        AnimatedVisibility(
            visible = showSuccess,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300)),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Success checkmark
                    var checkVisible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        delay(200)
                        checkVisible = true
                    }
                    
                    val checkScale by animateFloatAsState(
                        targetValue = if (checkVisible) 1f else 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "checkScale"
                    )
                    
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(checkScale)
                            .shadow(16.dp, CircleShape)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF4CAF50),
                                        Color(0xFF8BC34A)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            fontSize = 48.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Login Berhasil!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Selamat datang, ${loginData?.user?.nama}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Display detected role
                    Text(
                        text = "Role: ${loginData?.user?.role?.uppercase()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }
        
        // Main content
        AnimatedVisibility(
            visible = !showSuccess,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
                    .graphicsLayer {
                        translationX = if (shakeError) (sin(shakeOffset * Math.PI * 6) * 12).toFloat() else 0f
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                
                // Animated Logo
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(800)) + scaleIn(
                        initialScale = 0.5f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .scale(logoScale)
                            .shadow(
                                elevation = (12 * logoGlow).dp,
                                shape = CircleShape,
                                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = logoGlow),
                                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = logoGlow)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_smenda),
                            contentDescription = "Logo SMKN 2 Buduran Sidoarjo",
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Title
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(600, delayMillis = 200)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(600, delayMillis = 200)
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Selamat Datang",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                        Text(
                            text = "SMKN 2 Buduran",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Email Field with animation
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(600, delayMillis = 400)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(600, delayMillis = 400)
                    )
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = Primary
                            )
                        },
                        isError = email.isNotEmpty() && !isEmailValid,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = GlassBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = BorderLight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedLabelColor = Primary,
                            unfocusedLabelColor = TextSecondary,
                            errorBorderColor = Danger,
                            errorContainerColor = CardBackground
                        )
                    )
                }
                
                AnimatedVisibility(
                    visible = email.isNotEmpty() && !isEmailValid,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(
                        text = "Format email tidak valid",
                        color = Danger,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 8.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Password Field with animation
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(600, delayMillis = 500)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(600, delayMillis = 500)
                    )
                ) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = Primary
                            )
                        },
                        visualTransformation = if (passwordVisible) 
                            VisualTransformation.None 
                        else 
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = if (passwordVisible) 
                                        "Sembunyikan password" 
                                    else 
                                        "Tampilkan password",
                                    tint = Primary
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = GlassBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = BorderLight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedLabelColor = Primary,
                            unfocusedLabelColor = TextSecondary
                        )
                    )
                }
                
                // Error Message with animation
                AnimatedVisibility(
                    visible = errorMessage != null,
                    enter = fadeIn() + expandVertically() + slideInVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Danger.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Danger)
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = Danger,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Login Button with animation
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(600, delayMillis = 600)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(600, delayMillis = 600)
                    )
                ) {
                    Button(
                        onClick = {
                            buttonPressed = true
                            scope.launch {
                                delay(100)
                                buttonPressed = false
                                
                                isLoading = true
                                errorMessage = null
                                
                                val result = userRepository.login(email, password)
                                
                                result.onSuccess { data ->
                                    isLoading = false
                                    // Auto-detect role and proceed
                                    loginData = data
                                    showSuccess = true
                                }
                                
                                result.onFailure { error ->
                                    isLoading = false
                                    shakeError = true
                                    errorMessage = error.message ?: "Login gagal"
                                }
                            }
                        },
                        enabled = isEmailValid && password.isNotEmpty() && !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .scale(buttonScale)
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Primary, Secondary, Accent)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedContent(
                                targetState = isLoading,
                                transitionSpec = {
                                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                                },
                                label = "buttonContent"
                            ) { loading ->
                                if (loading) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        LoadingDotsAnimation()
                                    }
                                } else {
                                    Text(
                                        text = "Login",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}