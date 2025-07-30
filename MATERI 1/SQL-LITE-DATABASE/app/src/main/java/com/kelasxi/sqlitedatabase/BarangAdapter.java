package com.kelasxi.sqlitedatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Enhanced BarangAdapter with improved error handling, validation, and user experience
 */
public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {
    
    private static final String TAG = "BarangAdapter";
    private Context context;
    private List<Barang> barangList;
    private OnItemClickListener listener;
    
    // Interface for item click callbacks
    public interface OnItemClickListener {
        void onItemEdit(Barang barang);
        void onItemDelete(Barang barang);
        void onItemClick(Barang barang);
    }
    
    // Constructor
    public BarangAdapter(Context context, List<Barang> barangList) {
        this.context = context;
        this.barangList = barangList;
        
        if (context instanceof OnItemClickListener) {
            this.listener = (OnItemClickListener) context;
        }
        
        Log.d(TAG, "BarangAdapter created with " + (barangList != null ? barangList.size() : 0) + " items");
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.item_barang, parent, false);
            return new ViewHolder(view);
        } catch (Exception e) {
            Log.e(TAG, "Error creating ViewHolder", e);
            throw e;
        }
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (barangList == null || position >= barangList.size()) {
                Log.w(TAG, "Invalid position or null list: position=" + position);
                return;
            }
            
            Barang barang = barangList.get(position);
            if (barang == null) {
                Log.w(TAG, "Null barang at position: " + position);
                return;
            }
            
            // Bind data with proper formatting and validation
            bindData(holder, barang, position);
            
            // Set click listeners
            setClickListeners(holder, barang, position);
            
        } catch (Exception e) {
            Log.e(TAG, "Error binding data at position " + position, e);
        }
    }
    
    private void bindData(ViewHolder holder, Barang barang, int position) {
        try {
            // Set item name with validation
            String nama = barang.getNama();
            if (nama == null || nama.trim().isEmpty()) {
                nama = "Unnamed Item";
                holder.tvBarang.setTextColor(Color.GRAY);
            } else {
                holder.tvBarang.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            }
            holder.tvBarang.setText(nama);
            
            // Set stock with formatting and color coding
            int stok = barang.getStok();
            holder.tvStock.setText("Stock: " + barang.getFormattedStok());
            
            // Color code based on stock level
            if (barang.isOutOfStock()) {
                holder.tvStock.setTextColor(Color.RED);
            } else if (barang.isLowStock()) {
                holder.tvStock.setTextColor(Color.parseColor("#FF8C00")); // Orange
            } else {
                holder.tvStock.setTextColor(ContextCompat.getColor(context, R.color.text_secondary));
            }
            
            // Set formatted price
            holder.tvHarga.setText(barang.getFormattedHarga());
            holder.tvHarga.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            
            // Set menu icon
            holder.tvMenu.setText("⋮");
            holder.tvMenu.setTextColor(ContextCompat.getColor(context, R.color.text_secondary));
            
            Log.d(TAG, "Bound data for position " + position + ": " + nama);
            
        } catch (Exception e) {
            Log.e(TAG, "Error in bindData for position " + position, e);
        }
    }
    
    private void setClickListeners(ViewHolder holder, Barang barang, int position) {
        // Item click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(barang);
            } else {
                showItemDetails(barang);
            }
        });
        
        // Menu click listener
        holder.tvMenu.setOnClickListener(v -> showPopupMenu(holder.tvMenu, barang, position));
    }
    
    private void showPopupMenu(View anchor, Barang barang, int position) {
        try {
            PopupMenu popupMenu = new PopupMenu(context, anchor);
            popupMenu.inflate(R.menu.menu_item);
            
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                
                if (itemId == R.id.ubah) {
                    showUpdateDialog(barang, position);
                    return true;
                } else if (itemId == R.id.edit_form) {
                    if (listener != null) {
                        listener.onItemEdit(barang);
                    }
                    return true;
                } else if (itemId == R.id.hapus) {
                    showDeleteConfirmation(barang, position);
                    return true;
                } else {
                    return false;
                }
            });
            
            popupMenu.show();
            Log.d(TAG, "Popup menu shown for item: " + barang.getNama());
            
        } catch (Exception e) {
            Log.e(TAG, "Error showing popup menu", e);
            Toast.makeText(context, "Error showing menu", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void showUpdateDialog(Barang barang, int position) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Update Data Barang");
            builder.setIcon(R.drawable.ic_edit);
            
            // Create input layout
            LinearLayout layout = createUpdateLayout(barang);
            builder.setView(layout);
            
            // Get EditText references
            EditText etNama = (EditText) layout.getChildAt(0);
            EditText etStok = (EditText) layout.getChildAt(1);
            EditText etHarga = (EditText) layout.getChildAt(2);
            
            builder.setPositiveButton("Update", (dialog, which) -> {
                if (validateAndUpdate(barang, etNama, etStok, etHarga, position)) {
                    dialog.dismiss();
                }
            });
            
            builder.setNegativeButton("Batal", (dialog, which) -> dialog.dismiss());
            
            AlertDialog dialog = builder.create();
            dialog.show();
            
            // Style the buttons
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(context, R.color.primary_color));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(context, R.color.text_secondary));
            
        } catch (Exception e) {
            Log.e(TAG, "Error showing update dialog", e);
            Toast.makeText(context, "Error showing update dialog: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private LinearLayout createUpdateLayout(Barang barang) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);
        
        // Name input
        EditText etNama = new EditText(context);
        etNama.setHint("Nama Barang");
        etNama.setText(barang.getNama());
        etNama.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etNama.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        layout.addView(etNama);
        
        // Stock input
        EditText etStok = new EditText(context);
        etStok.setHint("Stok");
        etStok.setText(String.valueOf(barang.getStok()));
        etStok.setInputType(InputType.TYPE_CLASS_NUMBER);
        etStok.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        layout.addView(etStok);
        
        // Price input
        EditText etHarga = new EditText(context);
        etHarga.setHint("Harga");
        etHarga.setText(String.valueOf(barang.getHarga()));
        etHarga.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etHarga.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        layout.addView(etHarga);
        
        return layout;
    }
    
    private boolean validateAndUpdate(Barang barang, EditText etNama, EditText etStok, EditText etHarga, int position) {
        try {
            String nama = etNama.getText().toString().trim();
            String stokStr = etStok.getText().toString().trim();
            String hargaStr = etHarga.getText().toString().trim();
            
            // Validation
            if (nama.isEmpty()) {
                Toast.makeText(context, "Nama barang tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return false;
            }
            
            if (stokStr.isEmpty()) {
                Toast.makeText(context, "Stok tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return false;
            }
            
            if (hargaStr.isEmpty()) {
                Toast.makeText(context, "Harga tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return false;
            }
            
            int stok;
            double harga;
            
            try {
                stok = Integer.parseInt(stokStr);
                if (stok < 0) {
                    Toast.makeText(context, "Stok tidak boleh negatif", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Stok harus berupa angka bulat", Toast.LENGTH_SHORT).show();
                return false;
            }
            
            try {
                harga = Double.parseDouble(hargaStr);
                if (harga < 0) {
                    Toast.makeText(context, "Harga tidak boleh negatif", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Harga harus berupa angka", Toast.LENGTH_SHORT).show();
                return false;
            }
            
            // Update via listener
            if (listener != null) {
                try {
                    // Create updated Barang object with new values
                    Barang updatedBarang = new Barang(barang.getId(), nama, stok, harga);
                    listener.onItemEdit(updatedBarang);
                } catch (Exception e) {
                    Log.e(TAG, "Error calling listener for update", e);
                    Toast.makeText(context, "Error updating item", Toast.LENGTH_SHORT).show();
                }
            }
            
            return true;
            
        } catch (Exception e) {
            Log.e(TAG, "Error in validateAndUpdate", e);
            Toast.makeText(context, "Error updating item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    
    private void showDeleteConfirmation(Barang barang, int position) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Konfirmasi Hapus");
            builder.setMessage("Apakah Anda yakin ingin menghapus \"" + barang.getNama() + "\"?");
            builder.setIcon(R.drawable.ic_delete);
            
            builder.setPositiveButton("Hapus", (dialog, which) -> {
                if (listener != null) {
                    listener.onItemDelete(barang);
                }
                dialog.dismiss();
            });
            
            builder.setNegativeButton("Batal", (dialog, which) -> dialog.dismiss());
            
            AlertDialog dialog = builder.create();
            dialog.show();
            
            // Style the buttons
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(context, R.color.text_secondary));
            
        } catch (Exception e) {
            Log.e(TAG, "Error showing delete confirmation", e);
            Toast.makeText(context, "Error showing confirmation dialog", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void showItemDetails(Barang barang) {
        try {
            String details = "📦 " + barang.getNama() + "\n\n" +
                           "📊 Stok: " + barang.getFormattedStok() + "\n" +
                           "💰 Harga: " + barang.getFormattedHarga() + "\n" +
                           "💵 Total Nilai: " + barang.getFormattedTotalValue() + "\n\n" +
                           "📅 Dibuat: " + barang.getCreatedAt() + "\n" +
                           "🔄 Diupdate: " + barang.getUpdatedAt();
            
            new AlertDialog.Builder(context)
                .setTitle("Detail Barang")
                .setMessage(details)
                .setIcon(R.drawable.ic_info)
                .setPositiveButton("OK", null)
                .show();
                
        } catch (Exception e) {
            Log.e(TAG, "Error showing item details", e);
            Toast.makeText(context, "Error showing details", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public int getItemCount() {
        int count = barangList != null ? barangList.size() : 0;
        Log.d(TAG, "getItemCount: " + count);
        return count;
    }
    
    // Method to update the list
    public void updateList(List<Barang> newList) {
        try {
            this.barangList = newList;
            notifyDataSetChanged();
            Log.d(TAG, "List updated with " + (newList != null ? newList.size() : 0) + " items");
        } catch (Exception e) {
            Log.e(TAG, "Error updating list", e);
        }
    }
    
    // Method to add item
    public void addItem(Barang barang) {
        try {
            if (barangList != null && barang != null) {
                barangList.add(barang);
                notifyItemInserted(barangList.size() - 1);
                Log.d(TAG, "Item added: " + barang.getNama());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error adding item", e);
        }
    }
    
    // Method to remove item
    public void removeItem(int position) {
        try {
            if (barangList != null && position >= 0 && position < barangList.size()) {
                Barang removed = barangList.remove(position);
                notifyItemRemoved(position);
                Log.d(TAG, "Item removed: " + (removed != null ? removed.getNama() : "null"));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error removing item at position " + position, e);
        }
    }
    
    // ViewHolder inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBarang, tvStock, tvHarga, tvMenu;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                tvBarang = itemView.findViewById(R.id.tvBarang);
                tvStock = itemView.findViewById(R.id.tvStock);
                tvHarga = itemView.findViewById(R.id.tvHarga);
                tvMenu = itemView.findViewById(R.id.tvMenu);
                
                if (tvBarang == null || tvStock == null || tvHarga == null || tvMenu == null) {
                    Log.e(TAG, "One or more views not found in item layout");
                }
            } catch (Exception e) {
                Log.e(TAG, "Error initializing ViewHolder", e);
            }
        }
    }
}
