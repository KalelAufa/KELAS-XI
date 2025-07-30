package com.kelas.recyclerviewcardview;

import android.app.AlertDialog; // Ditambahkan
import android.content.DialogInterface; // Ditambahkan
import android.os.Bundle;
import android.text.InputType; // Ditambahkan
import android.widget.Button;
import android.widget.EditText; // Ditambahkan
import android.widget.LinearLayout; // Ditambahkan
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvSiswa;
    private SiswaAdapter siswaAdapter;
    private List<Siswa> listSiswa;
    private Button button;

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

        rcvSiswa = findViewById(R.id.rcvSiswa);
        button = findViewById(R.id.button);

        listSiswa = new ArrayList<>();
        listSiswa.add(new Siswa("John Doe", "Jl. Contoh No. 1"));
        listSiswa.add(new Siswa("Jane Smith", "Jl. Anggrek No. 5"));
        listSiswa.add(new Siswa("Peter Jones", "Jl. Mawar No. 10"));
        listSiswa.add(new Siswa("Alice Brown", "Jl. Melati No. 15"));
        listSiswa.add(new Siswa("Bob White", "Jl. Dahlia No. 20"));

        rcvSiswa.setLayoutManager(new LinearLayoutManager(this));
        siswaAdapter = new SiswaAdapter(listSiswa, this);
        rcvSiswa.setAdapter(siswaAdapter);

        button.setOnClickListener(v -> {
            showAddSiswaDialog(); // Panggil metode untuk menampilkan dialog penambahan siswa
        });
    }

    private void showAddSiswaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Siswa Baru");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20); // Tambahkan padding

        final EditText inputNama = new EditText(this);
        inputNama.setHint("Nama Siswa");
        inputNama.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputNama);

        final EditText inputAlamat = new EditText(this);
        inputAlamat.setHint("Alamat Siswa");
        inputAlamat.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputAlamat);

        builder.setView(layout);

        builder.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = inputNama.getText().toString().trim();
                String alamat = inputAlamat.getText().toString().trim();

                if (!nama.isEmpty() && !alamat.isEmpty()) {
                    listSiswa.add(new Siswa(nama, alamat));
                    siswaAdapter.notifyItemInserted(listSiswa.size() - 1); // Beritahu adapter bahwa item baru ditambahkan
                    rcvSiswa.scrollToPosition(listSiswa.size() - 1); // Gulir ke item yang baru ditambahkan
                    Toast.makeText(MainActivity.this, "Siswa " + nama + " berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Nama dan Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}