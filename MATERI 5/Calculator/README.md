# Calculator App - MVVM Architecture

Aplikasi kalkulator Android yang dibangun menggunakan **Jetpack Compose** dan **MVVM Architecture**.

## 📋 Fitur Utama

### ✅ Tugas 1: Arsitektur MVVM

1. **CalculatorViewModel**
   - `equationText`: MutableLiveData untuk menyimpan ekspresi matematika
   - `resultText`: MutableLiveData untuk menampilkan hasil perhitungan real-time
   - `onButtonClick()`: Entry point untuk menangani klik tombol
     - **AC**: Membersihkan semua (Clear All)
     - **C**: Menghapus karakter terakhir (Backspace)
     - **=**: Memindahkan hasil ke ekspresi
     - **Angka & Operator**: Menambahkan ke ekspresi
2. **Logika Perhitungan**
   - Menggunakan **exp4j library** untuk evaluasi ekspresi matematika
   - Pembersihan hasil: Menghapus ".0" dari bilangan bulat
   - Perhitungan real-time saat mengetik

### ✅ Tugas 2: Desain Antarmuka (UI)

1. **Daftar Tombol**

   - Grid 4x5 tombol (AC, C, angka 0-9, operator +, -, \*, /, =, dan titik desimal)

2. **Struktur Tata Letak**

   - `Column` utama dengan `Spacer(weight=1f)` untuk mendorong tombol ke bawah
   - Background gelap yang elegan (Dark Theme)

3. **Area Tampilan**

   - **Ekspresi**: Teks abu-abu 32sp, aligned ke kanan
   - **Hasil**: Teks putih bold 60sp, aligned ke kanan

4. **Tombol Kalkulator**

   - `LazyVerticalGrid` dengan 4 kolom
   - `FloatingActionButton` berbentuk lingkaran (CircleShape)
   - Ukuran seragam: 80dp
   - Spacing: 12dp antar tombol

5. **Pewarnaan Dinamis**
   - **AC & C**: Merah (#D32F2F)
   - **Operator (+, -, \*, /, =)**: Oranye (#FF9500)
   - **Angka & Titik**: Abu-abu gelap (#505050)

## 🛠️ Teknologi yang Digunakan

- **Kotlin**: Bahasa pemrograman
- **Jetpack Compose**: UI Framework modern
- **MVVM Architecture**: Memisahkan logika bisnis dari UI
- **LiveData**: Untuk reactive data binding
- **exp4j**: Library untuk evaluasi ekspresi matematika
- **Material Design 3**: Komponen UI modern

## 📦 Dependencies

```kotlin
// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
implementation("androidx.compose.runtime:runtime-livedata")

// exp4j for mathematical expression evaluation
implementation("net.objecthunter:exp4j:0.4.8")
```

## 🚀 Cara Menjalankan

1. Buka project di Android Studio
2. Sync Gradle (File → Sync Project with Gradle Files)
3. Jalankan aplikasi di emulator atau device fisik (Run → Run 'app')

## 📱 Screenshot

Aplikasi menampilkan:

- Ekspresi matematika di bagian atas
- Hasil perhitungan real-time di bawahnya
- Grid tombol kalkulator dengan warna yang berbeda-beda
- Dark theme yang nyaman di mata

## 🎯 Cara Penggunaan

1. Ketik angka dan operator menggunakan tombol
2. Hasil akan muncul secara real-time di bawah ekspresi
3. Tekan **=** untuk memindahkan hasil ke ekspresi
4. Tekan **C** untuk menghapus karakter terakhir
5. Tekan **AC** untuk membersihkan semua

## 👨‍💻 Developer

Dibuat untuk memenuhi tugas KELAS XI - MATERI 5

---

**Note**: Pastikan untuk melakukan Gradle Sync setelah membuka project untuk pertama kali!
