package com.kelas.recyclerviewcardview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem; // Ditambahkan
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu; // Ditambahkan
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.ViewHolder> {
    public Context context;
    public List<Siswa> listSiswa;

    public SiswaAdapter(List<Siswa> listSiswa, Context context) {
        this.listSiswa = listSiswa;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_siswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Siswa siswa = listSiswa.get(position);
        holder.tvNama.setText(siswa.getNama());
        holder.tvAlamat.setText(siswa.getAlamat());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Nama: " + siswa.getNama() + "\nAlamat: " + siswa.getAlamat();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

        // Implementasi PopupMenu untuk tvMenu
        holder.tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.tvMenu);
                popupMenu.inflate(R.menu.menu_option); // Menggunakan menu_option.xml
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.menu_simpan) {
                            Toast.makeText(context, "Menyimpan data " + siswa.getNama(), Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.menu_hapus) {
                            // Menghapus item dari daftar dan memberitahu adapter
                            listSiswa.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Menghapus data " + siswa.getNama(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSiswa.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvAlamat, tvMenu; // Deklarasi tvMenu

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvMenu = itemView.findViewById(R.id.tvMenu); // Inisialisasi tvMenu
        }
    }
}