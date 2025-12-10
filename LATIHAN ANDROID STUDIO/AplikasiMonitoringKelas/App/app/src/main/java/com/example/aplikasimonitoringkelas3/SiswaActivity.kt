package com.example.aplikasimonitoringkelas3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasimonitoringkelas3.data.api.RetrofitClient
import com.example.aplikasimonitoringkelas3.data.model.*
import com.example.aplikasimonitoringkelas3.data.repository.SiswaRepository
import com.example.aplikasimonitoringkelas3.ui.theme.AplikasiMonitoringKelas3Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SiswaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikasiMonitoringKelas3Theme {
                SiswaScreen(
                    onLogout = {
                        RetrofitClient.clearToken()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaScreen(onLogout: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    val tabs = listOf(
        TabItem("Home", Icons.Default.Home),
        TabItem("Jadwal", Icons.Default.DateRange),
        TabItem("Kehadiran", Icons.Default.Person),
        TabItem("Profile", Icons.Default.AccountCircle)
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                tonalElevation = 8.dp
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                    fadeOut(animationSpec = tween(300))
                },
                label = "tab_content"
            ) { tab ->
                when (tab) {
                    0 -> HomePage()
                    1 -> JadwalPage()
                    2 -> KehadiranPage()
                    3 -> ProfilePage(onLogout)
                }
            }
        }
    }
}

data class TabItem(val title: String, val icon: ImageVector)

@Composable
fun HomePage() {
    val repository = remember { SiswaRepository() }
    val scope = rememberCoroutineScope()
    
    var jadwalHariIni by remember { mutableStateOf<List<Jadwal>>(emptyList()) }
    var kelasInfo by remember { mutableStateOf<Kelas?>(null) }
    var hariIni by remember { mutableStateOf("") }
    var tanggalIni by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = repository.getJadwalHariIni()
                if (response.isSuccessful && response.body()?.success == true) {
                    val data = response.body()?.data
                    jadwalHariIni = data?.jadwal ?: emptyList()
                    kelasInfo = data?.kelas
                    hariIni = data?.hari ?: ""
                    tanggalIni = data?.tanggal ?: ""
                } else {
                    error = response.body()?.message ?: "Gagal memuat jadwal"
                }
            } catch (e: Exception) {
                error = e.message ?: "Error tidak diketahui"
            } finally {
                isLoading = false
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Selamat Datang!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                kelasInfo?.let {
                    Text(
                        text = "Kelas: ${it.namaKelas}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (hariIni.isNotEmpty()) "$hariIni, $tanggalIni" else "Loading...",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        
        // Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "📚 Jadwal Hari Ini",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else if (error != null) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = error ?: "Error",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            } else if (jadwalHariIni.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Tidak ada jadwal hari ini",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Waktunya istirahat! 🎉",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                itemsIndexed(jadwalHariIni) { index, jadwal ->
                    JadwalCard(jadwal = jadwal, index = index)
                }
            }
        }
    }
}

@Composable
fun JadwalCard(jadwal: Jadwal, index: Int) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(index) {
        delay(index * 100L)
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(initialOffsetX = { it })
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Time Column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = jadwal.jamMulai,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "sampai",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = jadwal.jamSelesai,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Info Column
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = jadwal.mataPelajaran,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = jadwal.guru?.nama ?: "Guru",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    jadwal.ruangan?.let { ruangan ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Place,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = ruangan,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JadwalPage() {
    val repository = remember { SiswaRepository() }
    val scope = rememberCoroutineScope()
    
    var jadwalPerHari by remember { mutableStateOf<Map<String, List<Jadwal>>>(emptyMap()) }
    var kelasInfo by remember { mutableStateOf<Kelas?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedDay by remember { mutableStateOf("Senin") }
    
    val days = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
    
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = repository.getJadwalByKelas()
                if (response.isSuccessful && response.body()?.success == true) {
                    val data = response.body()?.data
                    jadwalPerHari = data?.jadwal ?: emptyMap()
                    kelasInfo = data?.kelas
                } else {
                    error = response.body()?.message ?: "Gagal memuat jadwal"
                }
            } catch (e: Exception) {
                error = e.message ?: "Error tidak diketahui"
            } finally {
                isLoading = false
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "📅 Jadwal Pelajaran",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                kelasInfo?.let {
                    Text(
                        text = it.namaKelas,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
        
        // Day Tabs
        ScrollableTabRow(
            selectedTabIndex = days.indexOf(selectedDay),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 8.dp
        ) {
            days.forEach { day ->
                Tab(
                    selected = selectedDay == day,
                    onClick = { selectedDay = day },
                    text = { 
                        Text(
                            day,
                            fontWeight = if (selectedDay == day) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
        
        // Content
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = error ?: "Error",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        } else {
            val jadwalHari = jadwalPerHari[selectedDay] ?: emptyList()
            
            if (jadwalHari.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tidak ada jadwal pada hari $selectedDay",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(jadwalHari) { index, jadwal ->
                        JadwalCard(jadwal = jadwal, index = index)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KehadiranPage() {
    val repository = remember { SiswaRepository() }
    val scope = rememberCoroutineScope()
    
    var attendanceData by remember { mutableStateOf<TeacherAttendanceTodayData?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedGuru by remember { mutableStateOf<GuruAttendanceStatus?>(null) }
    var selectedStatus by remember { mutableStateOf("Hadir") }
    var keterangan by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    
    val statusOptions = listOf("Hadir", "Terlambat", "Tidak Hadir", "Izin")
    val statusColors = mapOf(
        "Hadir" to Color(0xFF4CAF50),
        "Terlambat" to Color(0xFFFF9800),
        "Tidak Hadir" to Color(0xFFF44336),
        "Izin" to Color(0xFF2196F3)
    )
    
    fun loadData() {
        scope.launch {
            isLoading = true
            try {
                val response = repository.getTodayAttendance()
                if (response.isSuccessful && response.body()?.success == true) {
                    attendanceData = response.body()?.data
                    error = null
                } else {
                    error = response.body()?.message ?: "Gagal memuat data kehadiran"
                }
            } catch (e: Exception) {
                error = e.message ?: "Error tidak diketahui"
            } finally {
                isLoading = false
            }
        }
    }
    
    LaunchedEffect(Unit) {
        loadData()
    }
    
    // Submit attendance
    fun submitAttendance() {
        selectedGuru?.let { guruStatus ->
            scope.launch {
                isSubmitting = true
                try {
                    val request = TeacherAttendanceRequest(
                        guruId = guruStatus.guru.id,
                        status = selectedStatus,
                        keterangan = keterangan.ifEmpty { null }
                    )
                    val response = repository.createTeacherAttendance(request)
                    if (response.isSuccessful) {
                        snackbarMessage = "Kehadiran ${guruStatus.guru.nama} berhasil dicatat!"
                        showDialog = false
                        loadData()
                    } else {
                        snackbarMessage = "Gagal mencatat kehadiran"
                    }
                } catch (e: Exception) {
                    snackbarMessage = "Error: ${e.message}"
                } finally {
                    isSubmitting = false
                }
            }
        }
    }
    
    Scaffold(
        snackbarHost = {
            snackbarMessage?.let { message ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { snackbarMessage = null }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(message)
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A2E),
                            Color(0xFF16213E)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Header dengan desain modern
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF667EEA),
                                    Color(0xFF764BA2)
                                )
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 28.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon Badge
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "📋",
                                fontSize = 28.sp
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = "Kehadiran Guru",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            attendanceData?.let { data ->
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = null,
                                        tint = Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = data.tanggalFormatted,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Summary Cards dengan desain glassmorphism
                attendanceData?.summary?.let { summary ->
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            ModernSummaryCard(
                                title = "Hadir",
                                count = summary.hadir,
                                total = summary.totalGuru,
                                color = Color(0xFF10B981),
                                icon = "✓"
                            )
                        }
                        item {
                            ModernSummaryCard(
                                title = "Terlambat",
                                count = summary.terlambat,
                                total = summary.totalGuru,
                                color = Color(0xFFF59E0B),
                                icon = "⏰"
                            )
                        }
                        item {
                            ModernSummaryCard(
                                title = "Tidak Hadir",
                                count = summary.tidakHadir,
                                total = summary.totalGuru,
                                color = Color(0xFFEF4444),
                                icon = "✗"
                            )
                        }
                        item {
                            ModernSummaryCard(
                                title = "Izin",
                                count = summary.izin,
                                total = summary.totalGuru,
                                color = Color(0xFF3B82F6),
                                icon = "🏥"
                            )
                        }
                    }
                }
                
                // Guru List dengan header section
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF667EEA))
                    }
                } else if (error != null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text(
                                text = "😔",
                                fontSize = 48.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = error ?: "Error",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { loadData() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF667EEA)
                                )
                            ) {
                                Text("Coba Lagi")
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Daftar Guru",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${attendanceData?.guruStatus?.size ?: 0} Guru",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                            }
                        }
                    
                        items(attendanceData?.guruStatus ?: emptyList()) { guruStatus ->
                            ModernGuruCard(
                                guruStatus = guruStatus,
                                statusColors = statusColors,
                                onClick = {
                                    selectedGuru = guruStatus
                                    selectedStatus = guruStatus.attendance?.status ?: "Hadir"
                                    keterangan = guruStatus.attendance?.keterangan ?: ""
                                    showDialog = true
                                }
                            )
                        }
                        
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
        
        // Attendance Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { 
                    Text(
                        "Input Kehadiran",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column {
                        Text(
                            text = selectedGuru?.guru?.nama ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text("Status Kehadiran:", fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            statusOptions.take(2).forEach { status ->
                                FilterChip(
                                    selected = selectedStatus == status,
                                    onClick = { selectedStatus = status },
                                    label = { Text(status, fontSize = 12.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = statusColors[status]?.copy(alpha = 0.2f) ?: MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            statusOptions.drop(2).forEach { status ->
                                FilterChip(
                                    selected = selectedStatus == status,
                                    onClick = { selectedStatus = status },
                                    label = { Text(status, fontSize = 12.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = statusColors[status]?.copy(alpha = 0.2f) ?: MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = keterangan,
                            onValueChange = { keterangan = it },
                            label = { Text("Keterangan (opsional)") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 2
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { submitAttendance() },
                        enabled = !isSubmitting
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Simpan")
                        }
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}

@Composable
fun ModernSummaryCard(
    title: String,
    count: Int,
    total: Int,
    color: Color,
    icon: String
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(140.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B).copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = icon,
                    fontSize = 32.sp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "dari $total",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    count: Int,
    color: Color,
    icon: ImageVector
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun ModernGuruCard(
    guruStatus: GuruAttendanceStatus,
    statusColors: Map<String, Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B).copy(alpha = 0.7f)
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar dengan gradient
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF667EEA),
                                    Color(0xFF764BA2)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = guruStatus.guru.nama.take(1).uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Info Guru
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = guruStatus.guru.nama,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    guruStatus.guru.mataPelajaran?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
                
                // Status Badge Modern
                if (guruStatus.hasAttendance && guruStatus.attendance != null) {
                    val status = guruStatus.attendance.status
                    val color = statusColors[status] ?: Color(0xFF667EEA)
                    
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(color.copy(alpha = 0.2f))
                            .border(
                                1.dp,
                                color.copy(alpha = 0.5f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = status,
                            style = MaterialTheme.typography.labelMedium,
                            color = color,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF475569).copy(alpha = 0.3f))
                            .border(
                                1.dp,
                                Color(0xFF475569).copy(alpha = 0.5f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Belum Input",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Info Guru Pengganti dengan desain modern
            if (guruStatus.digantikan != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFA726).copy(alpha = 0.15f)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0xFFFFA726).copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFFA726).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🔄",
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Digantikan oleh",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFFFA726),
                                fontWeight = FontWeight.Bold
                            )
                            guruStatus.digantikan.guruPengganti?.let { pengganti ->
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = pengganti.nama,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                            guruStatus.digantikan.alasan?.let { alasan ->
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Alasan: $alasan",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
            
            if (guruStatus.menggantikan != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF10B981).copy(alpha = 0.15f)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF10B981)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Menggantikan",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF10B981),
                                fontWeight = FontWeight.Bold
                            )
                            guruStatus.menggantikan.guruAsli?.let { asli ->
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = asli.nama,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                            guruStatus.menggantikan.jadwal?.let { jadwal ->
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = jadwal.mataPelajaran,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        text = " • ",
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                    Text(
                                        text = "${jadwal.jamMulai}-${jadwal.jamSelesai}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GuruAttendanceCard(
    guruStatus: GuruAttendanceStatus,
    statusColors: Map<String, Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = guruStatus.guru.nama.take(1).uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = guruStatus.guru.nama,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    guruStatus.guru.mataPelajaran?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Status Badge
                if (guruStatus.hasAttendance && guruStatus.attendance != null) {
                    val status = guruStatus.attendance.status
                    val color = statusColors[status] ?: MaterialTheme.colorScheme.primary
                    
                    Surface(
                        color = color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = status,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = color,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Surface(
                        color = Color.Gray.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Belum Input",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Informasi Guru Pengganti
            if (guruStatus.digantikan != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFE082).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFFF57C00),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Digantikan oleh:",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFF57C00),
                                fontWeight = FontWeight.Bold
                            )
                            guruStatus.digantikan.guruPengganti?.let { pengganti ->
                                Text(
                                    text = pengganti.nama,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF8B4000)
                                )
                            }
                            guruStatus.digantikan.alasan?.let { alasan ->
                                Text(
                                    text = "Alasan: $alasan",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF8B4000).copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
            
            if (guruStatus.menggantikan != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF81C784).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Menggantikan:",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold
                            )
                            guruStatus.menggantikan.guruAsli?.let { asli ->
                                Text(
                                    text = asli.nama,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1B5E20)
                                )
                            }
                            guruStatus.menggantikan.jadwal?.let { jadwal ->
                                Text(
                                    text = "${jadwal.mataPelajaran} • ${jadwal.jamMulai}-${jadwal.jamSelesai}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF1B5E20).copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfilePage(onLogout: () -> Unit) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Siswa",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Text(
                    text = "Aplikasi Monitoring Kelas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
        
        // Menu Items
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProfileMenuItem(
                icon = Icons.Default.Info,
                title = "Tentang Aplikasi",
                onClick = { }
            )
            
            ProfileMenuItem(
                icon = Icons.Default.Settings,
                title = "Pengaturan",
                onClick = { }
            )
            
            ProfileMenuItem(
                icon = Icons.Default.ExitToApp,
                title = "Keluar",
                onClick = { showLogoutDialog = true },
                textColor = MaterialTheme.colorScheme.error
            )
        }
    }
    
    // Logout Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Konfirmasi Keluar") },
            text = { Text("Apakah Anda yakin ingin keluar dari aplikasi?") },
            confirmButton = {
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Keluar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = textColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
