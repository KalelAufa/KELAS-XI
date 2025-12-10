# State Example - Android Compose

Proyek pembelajaran untuk memahami state management di Android Compose dengan implementasi `remember`, `rememberSaveable`, dan `ViewModel`.

## Fitur

- 🔄 **remember**: State lokal yang hilang saat recomposition
- 💾 **rememberSaveable**: State yang bertahan saat configuration change
- 🏗️ **ViewModel**: State hoisting dengan separation of concerns
- 📱 **Responsive UI**: Dua input fields dengan real-time update
- 🔄 **Configuration Change**: Testing rotasi layar

## Cara Menjalankan

1. Buka proyek di Android Studio
2. Sync gradle dependencies
3. Jalankan di emulator atau device
4. Test rotasi layar untuk melihat perbedaan behavior

## File Penting

- `MainActivity.kt`: Implementasi ketiga approach state management
- `StateTestViewModel.kt`: ViewModel dengan LiveData untuk state hoisting
- `STATE_MANAGEMENT_EXPLANATION.md`: Penjelasan detail tentang implementasi

## Testing Scenario

### Scenario 1: remember vs rememberSaveable

- Ganti `MyScreenWithViewModel()` ke `MyScreen()` di MainActivity
- Input teks, putar layar → teks hilang
- Ganti ke `MyScreenWithSaveable()`
- Input teks, putar layar → teks tetap ada

### Scenario 2: ViewModel State Management

- Gunakan `MyScreenWithViewModel()` (default)
- Isi first name dan last name
- Putar layar → semua state bertahan
- Lihat bagaimana full name terbentuk otomatis

## Struktur State Management

```
remember
├── State lokal di composable
├── Hilang saat recomposition
└── Cocok untuk temporary state

rememberSaveable
├── State bertahan saat config change
├── Auto save/restore
└── Cocok untuk important user input

ViewModel + LiveData
├── State hoisting
├── Separation of concerns
├── Shareable state
└── Cocok untuk complex apps
```

## Dependencies

```kotlin
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
implementation("androidx.compose.runtime:runtime-livedata:1.7.0")
```

## Pembelajaran

Proyek ini mengajarkan:

1. Perbedaan antara `remember` dan `rememberSaveable`
2. Kapan menggunakan ViewModel untuk state management
3. Implementasi LiveData dengan Compose
4. State hoisting dan separation of concerns
5. Handling configuration changes di Android

---

**Dibuat untuk Kelas XI - Materi 5: State Management**
