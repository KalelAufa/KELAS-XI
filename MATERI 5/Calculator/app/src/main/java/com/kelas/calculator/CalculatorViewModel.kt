package com.kelas.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    
    // MutableLiveData untuk menyimpan ekspresi matematika
    private val _equationText = MutableLiveData<String>("")
    val equationText: LiveData<String> = _equationText
    
    // MutableLiveData untuk menyimpan hasil perhitungan
    private val _resultText = MutableLiveData<String>("")
    val resultText: LiveData<String> = _resultText
    
    /**
     * Fungsi utama yang dipanggil setiap kali tombol diklik
     * @param button Label tombol yang diklik
     */
    fun onButtonClick(button: String) {
        when (button) {
            // Logika Pembersihan (AC)
            "AC" -> {
                _equationText.value = ""
                _resultText.value = ""
            }
            
            // Logika Penghapusan Belakang (C)
            "C" -> {
                val currentEquation = _equationText.value ?: ""
                if (currentEquation.isNotEmpty()) {
                    _equationText.value = currentEquation.dropLast(1)
                }
            }
            
            // Logika Tanda Sama Dengan (=)
            "=" -> {
                _equationText.value = _resultText.value
                return
            }
            
            // Logika Konkatenasi untuk angka dan operator
            else -> {
                _equationText.value = (_equationText.value ?: "") + button
            }
        }
        
        // Hitung hasil setiap kali ada perubahan pada equationText
        val equation = _equationText.value ?: ""
        if (equation.isNotEmpty()) {
            _resultText.value = calculateResult(equation)
        } else {
            _resultText.value = ""
        }
    }
    
    /**
     * Fungsi privat untuk menghitung hasil dari ekspresi matematika
     * Menggunakan exp4j library untuk evaluasi
     * @param equation Ekspresi matematika yang akan dihitung
     * @return Hasil perhitungan dalam bentuk String
     */
    private fun calculateResult(equation: String): String {
        return try {
            // Membuat expression builder dan evaluasi
            val expression = ExpressionBuilder(equation).build()
            val result = expression.evaluate()
            
            // Pembersihan hasil: hapus ".0" untuk bilangan bulat
            if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
            
        } catch (e: Exception) {
            // Jika terjadi error (misal: ekspresi tidak valid), kembalikan string kosong
            ""
        }
    }
}
