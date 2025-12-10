# Weather App - Real-Time Weather dengan Retrofit & Jetpack Compose

Aplikasi cuaca real-time yang dibangun menggunakan Retrofit dan Jetpack Compose dengan arsitektur MVVM.

## 🌟 Fitur

- ✅ Pencarian cuaca berdasarkan nama kota
- ✅ Menampilkan informasi cuaca real-time
- ✅ Tampilan informasi detail (suhu, kelembaban, kecepatan angin, tekanan, UV, visibilitas, dll)
- ✅ Icon cuaca yang dinamis
- ✅ Loading indicator saat mengambil data
- ✅ Error handling untuk kota yang tidak valid
- ✅ Modern UI dengan Material Design 3

## 🛠️ Teknologi yang Digunakan

- **Kotlin** - Bahasa pemrograman
- **Jetpack Compose** - UI Framework modern
- **Retrofit** - HTTP client untuk API calls
- **Gson** - JSON parsing
- **Coil** - Image loading untuk icon cuaca
- **LiveData** - Observasi data reactive
- **ViewModel** - Arsitektur MVVM
- **Coroutines** - Asynchronous programming

## 📋 Pra-syarat

1. **Android Studio** (versi terbaru)
2. **JDK 11** atau lebih tinggi
3. **API Key dari WeatherAPI**

## 🔑 Mendapatkan API Key

1. Kunjungi [WeatherAPI.com](https://www.weatherapi.com/)
2. Daftar akun gratis
3. Setelah login, pergi ke dashboard Anda
4. Salin API Key Anda

## 🚀 Cara Setup

### 1. Clone atau Download Project

```bash
git clone <repository-url>
```

### 2. Buka Project di Android Studio

- Buka Android Studio
- Pilih "Open an Existing Project"
- Navigasi ke folder project dan buka

### 3. Konfigurasi API Key

Buka file `WeatherViewModel.kt` di:

```
app/src/main/java/com/kelas/realtimewheater/viewmodel/WeatherViewModel.kt
```

Ganti `YOUR_API_KEY_HERE` dengan API Key yang Anda dapatkan:

```kotlin
private val API_KEY = "masukkan_api_key_anda_disini"
```

**Contoh:**

```kotlin
private val API_KEY = "a1b2c3d4e5f6g7h8i9j0"
```

### 4. Sync Gradle

- Setelah membuka project, tunggu Android Studio melakukan sync Gradle
- Jika diminta, klik "Sync Now"

### 5. Build & Run

- Pastikan device/emulator sudah terhubung
- Klik tombol "Run" (▶️) atau tekan `Shift + F10`

## 📱 Cara Menggunakan Aplikasi

1. **Buka aplikasi** di device/emulator Anda
2. **Ketik nama kota** di kolom pencarian (contoh: "London", "Jakarta", "New York")
3. **Klik icon search** 🔍 atau tekan enter
4. **Lihat informasi cuaca** yang muncul:
   - Nama kota dan negara
   - Suhu saat ini (°C)
   - Icon kondisi cuaca
   - Deskripsi cuaca
   - Detail tambahan (kelembaban, kecepatan angin, tekanan, UV, dll)

## 🧪 Testing

### Test Positif ✅

- Cari kota populer seperti:
  - London
  - New York
  - Jakarta
  - Tokyo
  - Paris
- Pastikan data muncul lengkap dan icon cuaca dimuat

### Test Loading ⏳

- Perhatikan CircularProgressIndicator muncul saat data sedang diambil

### Test Error ❌

- Cari nama kota yang tidak valid (contoh: "asdfghjkl")
- Pastikan pesan error muncul

## 📂 Struktur Project

```
app/src/main/java/com/kelas/realtimewheater/
├── api/
│   ├── WeatherModel.kt          # Data classes untuk response API
│   ├── WeatherAPI.kt             # Interface Retrofit
│   ├── RetrofitInstance.kt       # Singleton Retrofit instance
│   └── NetworkResponse.kt        # Sealed class untuk status response
├── viewmodel/
│   └── WeatherViewModel.kt       # ViewModel dengan logic API call
├── ui/
│   ├── screens/
│   │   ├── WeatherPage.kt        # UI utama dengan form pencarian
│   │   └── WeatherDetails.kt     # UI detail informasi cuaca
│   └── theme/                    # Theme configuration
└── MainActivity.kt               # Activity utama
```

## 🔄 Alur Kerja Aplikasi

1. **User input** → Ketik nama kota
2. **Click search** → Trigger `viewModel.getData(city)`
3. **Loading state** → Tampilkan CircularProgressIndicator
4. **API call** → Retrofit memanggil WeatherAPI
5. **Response handling:**
   - ✅ **Success** → Tampilkan WeatherDetails dengan data
   - ❌ **Error** → Tampilkan pesan error
6. **UI update** → LiveData diobservasi oleh Compose

## 🌐 API Endpoint

**Base URL:** `https://api.weatherapi.com/`

**Endpoint:** `/v1/current.json`

**Parameters:**

- `key`: API Key Anda
- `q`: Nama kota yang dicari

**Example Request:**

```
https://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=London
```

## 📊 Data yang Ditampilkan

### Informasi Utama

- Nama kota dan negara
- Suhu (Celsius)
- Icon dan kondisi cuaca

### Detail Tambahan

- **Humidity** (Kelembaban)
- **Wind Speed** (Kecepatan Angin)
- **UV Index**
- **Pressure** (Tekanan)
- **Feels Like** (Terasa Seperti)
- **Visibility** (Visibilitas)
- **Local Time** (Waktu Lokal)

## ⚠️ Troubleshooting

### Aplikasi tidak bisa mengambil data

- Pastikan API Key sudah benar
- Cek koneksi internet
- Pastikan permission INTERNET sudah ditambahkan di AndroidManifest.xml

### Error "Unresolved reference"

- Lakukan Gradle Sync: File → Sync Project with Gradle Files
- Clean project: Build → Clean Project
- Rebuild project: Build → Rebuild Project

### Icon cuaca tidak muncul

- Pastikan Coil dependency sudah terinstall
- Cek koneksi internet

## 📝 Catatan Penting

- **API Key gratis** memiliki limit request per hari
- Jangan share API Key Anda di repository public
- Icon cuaca diambil dari URL API dengan ukuran 128x128

## 🎓 Referensi

Proyek ini dibuat berdasarkan tutorial:

- "Weather app with Retrofit 🔥 | Android Studio | Jetpack Compose 2024"

## 📄 License

Project ini dibuat untuk tujuan pembelajaran.

## 👨‍💻 Pengembang

Dibuat sebagai materi pembelajaran KELAS XI

---

**Selamat Mencoba! 🚀**

Jika ada pertanyaan atau masalah, silakan buka issue di repository ini.
