package com.kelasxi.sqlitedatabase;

import android.util.Log;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Enhanced Barang model class with proper data types, validation, and formatting
 */
public class Barang implements Serializable {
    
    private static final String TAG = "Barang";
    private static final long serialVersionUID = 1L;
    
    // Use proper data types instead of String for everything
    private String id;
    private String nama;
    private int stok;
    private double harga;
    private String createdAt;
    private String updatedAt;
    
    // Default constructor
    public Barang() {
        this.id = "";
        this.nama = "";
        this.stok = 0;
        this.harga = 0.0;
        this.createdAt = getCurrentTimestamp();
        this.updatedAt = getCurrentTimestamp();
    }
    
    // Constructor with all parameters
    public Barang(String id, String nama, int stok, double harga) {
        this.id = id != null ? id : "";
        this.nama = nama != null ? nama.trim() : "";
        this.stok = Math.max(0, stok); // Ensure stok is not negative
        this.harga = Math.max(0.0, harga); // Ensure harga is not negative
        this.createdAt = getCurrentTimestamp();
        this.updatedAt = getCurrentTimestamp();
    }
    
    // Constructor with timestamps
    public Barang(String id, String nama, int stok, double harga, String createdAt, String updatedAt) {
        this.id = id != null ? id : "";
        this.nama = nama != null ? nama.trim() : "";
        this.stok = Math.max(0, stok);
        this.harga = Math.max(0.0, harga);
        this.createdAt = createdAt != null ? createdAt : getCurrentTimestamp();
        this.updatedAt = updatedAt != null ? updatedAt : getCurrentTimestamp();
    }
    
    // Legacy constructor for backward compatibility
    @Deprecated
    public Barang(String idbarang, String barang, String stock, String harga) {
        this.id = idbarang != null ? idbarang : "";
        this.nama = barang != null ? barang.trim() : "";
        
        try {
            this.stok = stock != null && !stock.isEmpty() ? Integer.parseInt(stock) : 0;
        } catch (NumberFormatException e) {
            Log.w(TAG, "Invalid stock value: " + stock + ", setting to 0");
            this.stok = 0;
        }
        
        try {
            this.harga = harga != null && !harga.isEmpty() ? Double.parseDouble(harga) : 0.0;
        } catch (NumberFormatException e) {
            Log.w(TAG, "Invalid price value: " + harga + ", setting to 0.0");
            this.harga = 0.0;
        }
        
        this.createdAt = getCurrentTimestamp();
        this.updatedAt = getCurrentTimestamp();
    }
    
    // Getter methods
    public String getId() {
        return id;
    }
    
    public String getNama() {
        return nama;
    }
    
    public int getStok() {
        return stok;
    }
    
    public double getHarga() {
        return harga;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public String getUpdatedAt() {
        return updatedAt;
    }
    
    // Legacy getter methods for backward compatibility
    @Deprecated
    public String getIdbarang() {
        return getId();
    }
    
    @Deprecated
    public String getBarang() {
        return getNama();
    }
    
    @Deprecated
    public String getStock() {
        return String.valueOf(getStok());
    }
    
    // Setter methods with validation
    public void setId(String id) {
        this.id = id != null ? id : "";
    }
    
    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            Log.w(TAG, "Setting empty name for item");
        }
        this.nama = nama != null ? nama.trim() : "";
        updateTimestamp();
    }
    
    public void setStok(int stok) {
        if (stok < 0) {
            Log.w(TAG, "Negative stock value " + stok + " changed to 0");
            this.stok = 0;
        } else {
            this.stok = stok;
        }
        updateTimestamp();
    }
    
    public void setHarga(double harga) {
        if (harga < 0.0) {
            Log.w(TAG, "Negative price value " + harga + " changed to 0.0");
            this.harga = 0.0;
        } else {
            this.harga = harga;
        }
        updateTimestamp();
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt != null ? createdAt : getCurrentTimestamp();
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt != null ? updatedAt : getCurrentTimestamp();
    }
    
    // Legacy setter methods for backward compatibility
    @Deprecated
    public void setIdbarang(String idbarang) {
        setId(idbarang);
    }
    
    @Deprecated
    public void setBarang(String barang) {
        setNama(barang);
    }
    
    @Deprecated
    public void setStock(String stock) {
        try {
            int stokValue = stock != null && !stock.isEmpty() ? Integer.parseInt(stock) : 0;
            setStok(stokValue);
        } catch (NumberFormatException e) {
            Log.w(TAG, "Invalid stock value in setStock: " + stock);
            setStok(0);
        }
    }
    
    @Deprecated
    public void setHarga(String harga) {
        try {
            double hargaValue = harga != null && !harga.isEmpty() ? Double.parseDouble(harga) : 0.0;
            setHarga(hargaValue);
        } catch (NumberFormatException e) {
            Log.w(TAG, "Invalid price value in setHarga: " + harga);
            setHarga(0.0);
        }
    }
    
    // Utility methods
    public String getFormattedHarga() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(harga);
    }
    
    public String getFormattedStok() {
        return String.format(Locale.getDefault(), "%,d", stok);
    }
    
    public boolean isValidItem() {
        return nama != null && !nama.trim().isEmpty() && stok >= 0 && harga >= 0;
    }
    
    public boolean isLowStock() {
        return stok <= 5; // Consider items with 5 or less as low stock
    }
    
    public boolean isOutOfStock() {
        return stok == 0;
    }
    
    public double getTotalValue() {
        return stok * harga;
    }
    
    public String getFormattedTotalValue() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(getTotalValue());
    }
    
    private void updateTimestamp() {
        this.updatedAt = getCurrentTimestamp();
    }
    
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    
    @Override
    public String toString() {
        return "Barang{" +
                "id='" + id + '\'' +
                ", nama='" + nama + '\'' +
                ", stok=" + stok +
                ", harga=" + harga +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Barang barang = (Barang) o;
        
        return id != null ? id.equals(barang.id) : barang.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
