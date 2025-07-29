package com.kelas.intentactivity;

import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.widget.TextView; // Import TextView

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class barang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_barang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ambil Intent yang memulai aktivitas ini
        Intent intent = getIntent();
        // Dapatkan string extra menggunakan kunci yang sama seperti saat mengirim
        String namaBarang = intent.getStringExtra(MainActivity.EXTRA_NAMA_BARANG);

        // Cari TextView di layout dan set teksnya
        TextView textViewNamaBarangDiterima = findViewById(R.id.textViewNamaBarangDiterima);
        if (namaBarang != null && !namaBarang.isEmpty()) {
            textViewNamaBarangDiterima.setText("Nama Barang: " + namaBarang);
        } else {
            textViewNamaBarangDiterima.setText("Nama Barang: Tidak ada input");
        }
    }
}