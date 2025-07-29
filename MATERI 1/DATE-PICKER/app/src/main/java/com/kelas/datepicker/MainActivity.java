package com.kelas.datepicker; // Pastikan ini sesuai dengan nama package proyek Anda

import android.app.DatePickerDialog; // Import untuk DatePickerDialog
import android.os.Bundle;
import android.view.View; // Import untuk View
import android.widget.EditText; // Import untuk EditText
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar; // Import untuk Calendar
import java.util.Locale; // Import untuk Locale

public class MainActivity extends AppCompatActivity {

    private EditText editTextText; // Deklarasi variabel untuk EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi EditText
        editTextText = findViewById(R.id.editTextText);

        // Set OnClickListener untuk menampilkan DatePicker
        editTextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // +1 karena bulan dimulai dari 0 (Januari) hingga 11 (Desember)
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    editTextText.setText(date);
                },
                year, month, day);
        datePickerDialog.show();
    }
}