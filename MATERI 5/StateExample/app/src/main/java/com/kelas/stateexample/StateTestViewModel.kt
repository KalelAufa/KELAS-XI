package com.kelas.stateexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Tugas 3: ViewModel untuk state hoisting dan pemisahan logika
class StateTestViewModel : ViewModel() {
    
    // Private MutableLiveData untuk name
    private val _name = MutableLiveData<String>("")
    // Public LiveData untuk name (read-only)
    val name: LiveData<String> = _name
    
    // Private MutableLiveData untuk surname
    private val _surname = MutableLiveData<String>("")
    // Public LiveData untuk surname (read-only)
    val surname: LiveData<String> = _surname
    
    // Fungsi event untuk memperbarui name
    fun onNameUpdate(newName: String) {
        _name.value = newName
    }
    
    // Fungsi event untuk memperbarui surname
    fun onSurnameUpdate(newSurname: String) {
        _surname.value = newSurname
    }
    
    // Fungsi untuk mendapatkan nama lengkap
    fun getFullName(): String {
        val firstName = _name.value ?: ""
        val lastName = _surname.value ?: ""
        return if (firstName.isNotEmpty() || lastName.isNotEmpty()) {
            "$firstName $lastName".trim()
        } else {
            "Guest"
        }
    }
}