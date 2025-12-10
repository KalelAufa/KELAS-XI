# State Management di Android Compose

## Penjelasan Implementasi

Proyek ini mengimplementasikan tiga pendekatan berbeda untuk state management di Android Compose:

### 1. **remember** - State Lokal (MyScreen)

```kotlin
var name by remember { mutableStateOf("") }
```

**Karakteristik:**

- State disimpan selama composable tidak di-recompose
- **HILANG saat configuration change** (rotasi layar)
- Cocok untuk state sementara yang tidak penting

**Kapan menggunakan:**

- Form input sederhana
- State UI yang sifatnya sementara
- Tidak memerlukan persistensi

### 2. **rememberSaveable** - State yang Bertahan (MyScreenWithSaveable)

```kotlin
var name by rememberSaveable { mutableStateOf("") }
```

**Karakteristik:**

- State bertahan saat configuration change (rotasi layar)
- **BERTAHAN** meskipun activity di-recreate
- Otomatis menyimpan dan memulihkan state

**Kapan menggunakan:**

- Input form yang penting
- State yang harus bertahan saat rotasi
- User experience yang seamless

### 3. **ViewModel dengan LiveData** - State Hoisting (MyScreenWithViewModel)

```kotlin
val name by viewModel.name.observeAsState(initial = "")
```

**Karakteristik:**

- State dikelola di luar composable
- **Pemisahan tanggung jawab** (UI vs Logic)
- Bertahan selama lifecycle ViewModel
- Dapat dishare antar composable

**Kapan menggunakan:**

- Aplikasi kompleks dengan banyak state
- Sharing state antar screen
- Business logic yang kompleks
- Testing yang lebih mudah

## Struktur ViewModel

```kotlin
class StateTestViewModel : ViewModel() {
    // Private MutableLiveData - hanya ViewModel yang bisa mengubah
    private val _name = MutableLiveData<String>("")
    // Public LiveData - composable hanya bisa membaca
    val name: LiveData<String> = _name

    // Event function untuk mengubah state
    fun onNameUpdate(newName: String) {
        _name.value = newName
    }
}
```

## Pengujian

### Test 1: remember vs rememberSaveable

1. Jalankan app dengan `MyScreen` (remember)
2. Ketik teks di input field
3. Putar layar → **Teks hilang**
4. Ganti ke `MyScreenWithSaveable` (rememberSaveable)
5. Ketik teks di input field
6. Putar layar → **Teks tetap ada**

### Test 2: ViewModel State Management

1. Jalankan app dengan `MyScreenWithViewModel`
2. Isi both input fields (First Name & Last Name)
3. Putar layar → **Semua state tetap ada**
4. Perhatikan bahwa full name terbentuk dari kedua input

## Rekomendasi

| Skenario                       | Solusi             | Alasan                               |
| ------------------------------ | ------------------ | ------------------------------------ |
| Input sederhana, tidak penting | `remember`         | Lightweight, tidak perlu persistensi |
| Form input penting             | `rememberSaveable` | User experience yang baik            |
| Aplikasi kompleks              | `ViewModel`        | Separation of concerns, testability  |
| Sharing state                  | `ViewModel`        | Centralized state management         |

## Hasil Akhir

Aplikasi ini mendemonstrasikan:

- ✅ **State management** dengan tiga pendekatan berbeda
- ✅ **Input handling** yang responsive
- ✅ **Configuration change handling** (rotasi layar)
- ✅ **Separation of concerns** dengan ViewModel
- ✅ **Two-way data binding** dengan LiveData dan Compose

Semua composable berhasil mempertahankan data input sesuai dengan karakteristik masing-masing approach, dan ViewModel berhasil memisahkan logika state dari UI.
