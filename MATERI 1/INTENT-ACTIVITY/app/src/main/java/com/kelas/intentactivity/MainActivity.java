package com.kelas.intentactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText; // Import EditText

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAMA_BARANG = "com.kelas.intentactivity.EXTRA_NAMA_BARANG"; // Konstanta untuk kunci extra

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
    }

    public void openBarangActivity(View view) {
        EditText editTextNamaBarang = findViewById(R.id.editTextNamaBarang);
        String namaBarang = editTextNamaBarang.getText().toString(); // Ambil teks dari EditText

        Intent intent = new Intent(this, barang.class);
        intent.putExtra(EXTRA_NAMA_BARANG, namaBarang); // Masukkan teks ke Intent sebagai extra
        startActivity(intent);
    }

    public void openPenjualanActivity(View view) {
        Intent intent = new Intent(this, penjualan.class);
        startActivity(intent);
    }

    public void openPembelianActivity(View view) {
        Intent intent = new Intent(this, pembelian.class); // Mengarahkan ke PembelianActivity
        startActivity(intent);
    }
}