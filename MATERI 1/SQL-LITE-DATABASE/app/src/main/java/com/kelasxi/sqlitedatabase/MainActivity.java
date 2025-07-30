package com.kelasxi.sqlitedatabase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Enhanced MainActivity with improved database operations, error handling, and user experience
 */
public class MainActivity extends AppCompatActivity implements BarangAdapter.OnItemClickListener {
    
    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "barang_preferences";
    
    // Database and UI components
    private Database db;
    private EditText etBarang, etStok, etHarga, etSearch;
    private TextView tvPilihan, tvEmptyState, tvTotalItems, tvLowStockCount, tvTotalValue;
    private ImageView ivModeIcon;
    private RecyclerView rcvBarang;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MaterialCardView cardDataEntry;
    private ExtendedFloatingActionButton fabQuickAdd;
    private NestedScrollView nestedScrollView;
    private Toolbar toolbar;
    private BarangAdapter adapter;
    private List<Barang> dataBarang;
    
    // SharedPreferences
    private SharedPreferences sharedPreferences;
    
    // Update mode tracking
    private String updateId = null;
    private boolean isUpdateMode = false;
    
    // Search functionality
    private String currentSearchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            Log.d(TAG, "MainActivity onCreate started");
            setContentView(R.layout.activity_main);
            
            // Initialize components in proper order
            initializeDatabase();
            initializeSharedPreferences();
            initializeViews();
            initializeRecyclerView();
            setupSearchFunctionality();
            
            // Load initial data
            loadInitialData();
            
            // Load any saved preferences
            loadLastPreferences();
            
            Log.d(TAG, "MainActivity onCreate completed successfully");
            
        } catch (Exception e) {
            Log.e(TAG, "Critical error in onCreate", e);
            showErrorDialog("Initialization Error", "Failed to initialize the application: " + e.getMessage());
        }
    }
    
    private void initializeDatabase() {
        try {
            Log.d(TAG, "Initializing database");
            db = new Database(this);
            Log.d(TAG, "Database initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing database", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }
    
    private void initializeSharedPreferences() {
        try {
            Log.d(TAG, "Initializing SharedPreferences");
            sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            Log.d(TAG, "SharedPreferences initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing SharedPreferences", e);
        }
    }
    
    private void initializeViews() {
        try {
            Log.d(TAG, "Initializing views");
            
            // Setup Toolbar
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            
            // Form elements
            etBarang = findViewById(R.id.etBarang);
            etStok = findViewById(R.id.etStok);
            etHarga = findViewById(R.id.etHarga);
            etSearch = findViewById(R.id.etSearch);
            
            // Status and info elements
            tvPilihan = findViewById(R.id.tvPilihan);
            tvEmptyState = findViewById(R.id.tvEmptyState);
            tvTotalItems = findViewById(R.id.tvTotalItems);
            tvLowStockCount = findViewById(R.id.tvLowStockCount);
            tvTotalValue = findViewById(R.id.tvTotalValue);
            ivModeIcon = findViewById(R.id.ivModeIcon);
            
            // Layout elements
            rcvBarang = findViewById(R.id.recyclerView);
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            cardDataEntry = findViewById(R.id.cardDataEntry);
            fabQuickAdd = findViewById(R.id.fabQuickAdd);
            nestedScrollView = findViewById(R.id.main);
            
            // Validate critical views
            if (etBarang == null || etStok == null || etHarga == null || rcvBarang == null) {
                throw new RuntimeException("Critical UI elements not found");
            }
            
            // Set initial state
            updateModeIndicator("Add New Product", false);
            
            // Setup SwipeRefreshLayout
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setOnRefreshListener(this::refreshData);
                swipeRefreshLayout.setColorSchemeResources(
                    R.color.primary_color,
                    R.color.success_color,
                    R.color.info_color,
                    R.color.warning_color
                );
            }
            
            // Setup FAB
            if (fabQuickAdd != null) {
                fabQuickAdd.setOnClickListener(v -> scrollToForm());
            }
            
            Log.d(TAG, "Views initialized successfully");
            
        } catch (Exception e) {
            Log.e(TAG, "Error initializing views", e);
            throw new RuntimeException("View initialization failed", e);
        }
    }
    
    private void initializeRecyclerView() {
        try {
            Log.d(TAG, "Initializing RecyclerView");
            
            if (rcvBarang == null) {
                throw new RuntimeException("RecyclerView is null");
            }
            
            // Initialize data list
            dataBarang = new ArrayList<>();
            
            // Create and set adapter
            adapter = new BarangAdapter(this, dataBarang);
            rcvBarang.setAdapter(adapter);
            
            // Set layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rcvBarang.setLayoutManager(layoutManager);
            
            // Optional: Add item decoration for better spacing
            // rcvBarang.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            
            Log.d(TAG, "RecyclerView initialized successfully");
            
        } catch (Exception e) {
            Log.e(TAG, "Error initializing RecyclerView", e);
            throw new RuntimeException("RecyclerView initialization failed", e);
        }
    }
    
    private void setupSearchFunctionality() {
        try {
            Log.d(TAG, "Setting up search functionality");
            
            if (etSearch != null) {
                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // Not needed
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Update current search query
                        currentSearchQuery = s.toString().trim();
                        
                        // Perform search with debouncing
                        if (currentSearchQuery.isEmpty()) {
                            refreshData(); // Show all data
                        } else {
                            performSearch(currentSearchQuery);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // Not needed
                    }
                });
            }
            
            Log.d(TAG, "Search functionality setup completed");
            
        } catch (Exception e) {
            Log.e(TAG, "Error setting up search functionality", e);
        }
    }
    
    private void loadInitialData() {
        try {
            Log.d(TAG, "Loading initial data");
            refreshData();
            Log.d(TAG, "Initial data loaded successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error loading initial data", e);
            showToast("Error loading initial data");
        }
    }
    
    private void loadLastPreferences() {
        try {
            Log.d(TAG, "Loading last preferences");
            
            if (sharedPreferences != null) {
                String lastSearch = sharedPreferences.getString("last_search", "");
                if (etSearch != null && !lastSearch.isEmpty()) {
                    etSearch.setText(lastSearch);
                }
            }
            
            Log.d(TAG, "Last preferences loaded");
            
        } catch (Exception e) {
            Log.e(TAG, "Error loading last preferences", e);
        }
    }
    
    public void refreshData() {
        try {
            Log.d(TAG, "Refreshing data");
            
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
            }
            
            if (currentSearchQuery.isEmpty()) {
                selectData();
            } else {
                performSearch(currentSearchQuery);
            }
            
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error refreshing data", e);
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            showToast("Error refreshing data");
        }
    }
    
    private void performSearch(String query) {
        try {
            Log.d(TAG, "Performing search: " + query);
            
            if (dataBarang == null) {
                dataBarang = new ArrayList<>();
            }
            
            dataBarang.clear();
            Cursor cursor = db.searchByName(query);
            
            if (cursor != null) {
                processDataCursor(cursor);
                cursor.close();
            }
            
            updateUI();
            
        } catch (Exception e) {
            Log.e(TAG, "Error performing search", e);
            showToast("Search error: " + e.getMessage());
        }
    }
    
    private void processDataCursor(Cursor cursor) {
        try {
            if (cursor == null) {
                Log.w(TAG, "Cursor is null");
                return;
            }
            
            Log.d(TAG, "Processing cursor with " + cursor.getCount() + " records");
            
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int barangIndex = cursor.getColumnIndex("barang");
                    int stokIndex = cursor.getColumnIndex("stok");
                    int hargaIndex = cursor.getColumnIndex("harga");
                    
                    if (idIndex >= 0 && barangIndex >= 0 && stokIndex >= 0 && hargaIndex >= 0) {
                        String id = cursor.getString(idIndex);
                        String barang = cursor.getString(barangIndex);
                        int stok = cursor.getInt(stokIndex);
                        double harga = cursor.getDouble(hargaIndex);
                        
                        Barang item = new Barang(id, barang, stok, harga);
                        dataBarang.add(item);
                    }
                    
                } while (cursor.moveToNext());
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error processing cursor", e);
        }
    }
    
    private void updateUI() {
        try {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            
            if (dataBarang != null && !dataBarang.isEmpty()) {
                showRecyclerView();
                updateTotalItemsDisplay();
            } else {
                showEmptyState();
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error updating UI", e);
        }
    }
    
    private void showRecyclerView() {
        try {
            if (rcvBarang != null) rcvBarang.setVisibility(View.VISIBLE);
            if (tvEmptyState != null) tvEmptyState.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(TAG, "Error showing RecyclerView", e);
        }
    }
    
    private void showEmptyState() {
        try {
            if (rcvBarang != null) rcvBarang.setVisibility(View.GONE);
            if (tvEmptyState != null) tvEmptyState.setVisibility(View.VISIBLE);
            if (tvTotalItems != null) tvTotalItems.setText("Total: 0 items");
        } catch (Exception e) {
            Log.e(TAG, "Error showing empty state", e);
        }
    }
    
    private void updateTotalItemsDisplay() {
        try {
            if (dataBarang != null) {
                // Update total items count
                if (tvTotalItems != null) {
                    tvTotalItems.setText(String.valueOf(dataBarang.size()));
                }
                
                // Calculate low stock count (stock <= 5)
                int lowStockCount = 0;
                double totalValue = 0;
                
                for (Barang item : dataBarang) {
                    if (item.getStok() <= 5) {
                        lowStockCount++;
                    }
                    totalValue += (item.getStok() * item.getHarga());
                }
                
                // Update low stock count
                if (tvLowStockCount != null) {
                    tvLowStockCount.setText(String.valueOf(lowStockCount));
                }
                
                // Update total value
                if (tvTotalValue != null) {
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                    formatter.setMaximumFractionDigits(0);
                    String formattedValue = formatter.format(totalValue).replace("IDR", "Rp");
                    tvTotalValue.setText(formattedValue);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating total items display", e);
        }
    }
    
    private void updateModeIndicator(String modeText, boolean isUpdateMode) {
        try {
            if (tvPilihan != null) {
                tvPilihan.setText(modeText);
            }
            
            if (ivModeIcon != null) {
                if (isUpdateMode) {
                    ivModeIcon.setImageResource(R.drawable.ic_edit);
                } else {
                    ivModeIcon.setImageResource(R.drawable.ic_add);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating mode indicator", e);
        }
    }
    
    public void scrollToForm() {
        try {
            if (nestedScrollView != null && cardDataEntry != null) {
                nestedScrollView.post(() -> {
                    nestedScrollView.smoothScrollTo(0, cardDataEntry.getTop());
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error scrolling to form", e);
        }
    }
    
    // Database operations
    public void simpanData(View v) {
        try {
            Log.d(TAG, "simpanData called, isUpdateMode: " + isUpdateMode);
            
            String barang = etBarang.getText().toString().trim();
            String stokStr = etStok.getText().toString().trim();
            String hargaStr = etHarga.getText().toString().trim();
            
            // Validation
            if (barang.isEmpty() || stokStr.isEmpty() || hargaStr.isEmpty()) {
                showToast("Semua field harus diisi!");
                return;
            }
            
            try {
                int stok = Integer.parseInt(stokStr);
                double harga = Double.parseDouble(hargaStr);
                
                if (stok < 0 || harga < 0) {
                    showToast("Stok dan harga tidak boleh negatif!");
                    return;
                }
                
                if (isUpdateMode && updateId != null) {
                    // Update existing item
                    boolean result = db.updateBarang(updateId, barang, stok, harga);
                    if (result) {
                        showToast("Data berhasil diupdate!");
                        exitUpdateMode();
                    } else {
                        showToast("Gagal mengupdate data!");
                    }
                } else {
                    // Insert new item
                    long result = db.insertBarang(barang, stok, harga);
                    if (result != -1) {
                        showToast("Data berhasil disimpan!");
                    } else {
                        showToast("Gagal menyimpan data!");
                    }
                }
                
                clearForm();
                refreshData();
                
            } catch (NumberFormatException e) {
                showToast("Format angka tidak valid!");
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error in simpanData", e);
            showToast("Error: " + e.getMessage());
        }
    }
    
    public void selectData() {
        try {
            Log.d(TAG, "Selecting all data");
            
            if (dataBarang == null) {
                dataBarang = new ArrayList<>();
            }
            
            dataBarang.clear();
            Cursor cursor = db.selectAll();
            
            if (cursor != null) {
                processDataCursor(cursor);
                cursor.close();
            }
            
            updateUI();
            
        } catch (Exception e) {
            Log.e(TAG, "Error in selectData", e);
            showToast("Error loading data: " + e.getMessage());
        }
    }
    
    public void deleteData(String id, String itemName) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Konfirmasi Hapus");
            builder.setMessage("Yakin akan menghapus '" + itemName + "'?");
            
            builder.setPositiveButton("Ya", (dialog, which) -> {
                try {
                    boolean result = db.deleteBarang(id);
                    if (result) {
                        showToast("Data berhasil dihapus!");
                        refreshData();
                    } else {
                        showToast("Gagal menghapus data!");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error deleting data", e);
                    showToast("Error: " + e.getMessage());
                }
            });
            
            builder.setNegativeButton("Tidak", null);
            builder.show();
            
        } catch (Exception e) {
            Log.e(TAG, "Error in deleteData", e);
            showToast("Error: " + e.getMessage());
        }
    }
    
    // BarangAdapter.OnItemClickListener implementation
    @Override
    public void onItemEdit(Barang item) {
        try {
            Log.d(TAG, "Edit item: " + item.getNama());
            enterUpdateMode(item);
        } catch (Exception e) {
            Log.e(TAG, "Error in onItemEdit", e);
            showToast("Error editing item");
        }
    }
    
    @Override
    public void onItemDelete(Barang item) {
        try {
            Log.d(TAG, "Delete item: " + item.getNama());
            deleteData(item.getId(), item.getNama());
        } catch (Exception e) {
            Log.e(TAG, "Error in onItemDelete", e);
            showToast("Error deleting item");
        }
    }
    
    @Override
    public void onItemClick(Barang item) {
        try {
            Log.d(TAG, "Item clicked: " + item.getNama());
            showItemDetails(item);
        } catch (Exception e) {
            Log.e(TAG, "Error in onItemClick", e);
            showToast("Error showing item details");
        }
    }
    
    private void showItemDetails(Barang item) {
        try {
            String details = "📦 Item Details\n\n" +
                           "🏷️ Name: " + item.getNama() + "\n" +
                           "📊 Stock: " + item.getStok() + "\n" +
                           "💰 Price: " + item.getFormattedHarga() + "\n\n" +
                           "ℹ️ Status: " + (item.isLowStock() ? "⚠️ Low Stock" : "✅ Good Stock");
            
            new AlertDialog.Builder(this)
                .setTitle("Item Information")
                .setMessage(details)
                .setPositiveButton("OK", null)
                .setNegativeButton("Edit", (dialog, which) -> enterUpdateMode(item))
                .show();
                
        } catch (Exception e) {
            Log.e(TAG, "Error showing item details", e);
            showToast("Error showing details");
        }
    }
    
    private void enterUpdateMode(Barang item) {
        try {
            Log.d(TAG, "Entering update mode for item: " + item.getId());
            
            isUpdateMode = true;
            updateId = item.getId();
            
            etBarang.setText(item.getNama());
            etStok.setText(String.valueOf(item.getStok()));
            etHarga.setText(String.valueOf(item.getHarga()));
            
            updateModeIndicator("Edit Product: " + item.getNama(), true);
            
            // Scroll to form
            scrollToForm();
            
            showToast("Update mode active. Click SAVE to update product.");
            
        } catch (Exception e) {
            Log.e(TAG, "Error entering update mode", e);
            showToast("Error entering update mode");
        }
    }
    
    private void exitUpdateMode() {
        try {
            Log.d(TAG, "Exiting update mode");
            
            isUpdateMode = false;
            updateId = null;
            
            updateModeIndicator("Add New Product", false);
            
        } catch (Exception e) {
            Log.e(TAG, "Error exiting update mode", e);
        }
    }
    
    private void clearForm() {
        try {
            etBarang.setText("");
            etStok.setText("");
            etHarga.setText("");
            etBarang.requestFocus();
            
            exitUpdateMode();
            
        } catch (Exception e) {
            Log.e(TAG, "Error clearing form", e);
        }
    }
    
    // onClick methods for layout buttons
    public void searchItems(View v) {
        try {
            String query = etSearch != null ? etSearch.getText().toString().trim() : "";
            if (!query.isEmpty()) {
                performSearch(query);
                showToast("Searching for: " + query);
            } else {
                showToast("Enter search keyword");
                if (etSearch != null) {
                    etSearch.requestFocus();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in searchItems", e);
            showToast("Error searching items");
        }
    }
    
    public void showAllItems(View v) {
        try {
            if (etSearch != null) {
                etSearch.setText("");
            }
            currentSearchQuery = "";
            refreshData();
            showToast("Showing all items");
        } catch (Exception e) {
            Log.e(TAG, "Error in showAllItems", e);
            showToast("Error showing all items");
        }
    }
    
    public void filterLowStock(View v) {
        try {
            if (dataBarang == null) {
                dataBarang = new ArrayList<>();
            }
            
            dataBarang.clear();
            Cursor cursor = db.filterByStockRange(0, 5);
            
            if (cursor != null) {
                processDataCursor(cursor);
                cursor.close();
            }
            
            updateUI();
            showToast("Showing low stock items (≤5)");
            
        } catch (Exception e) {
            Log.e(TAG, "Error in filterLowStock", e);
            showToast("Error filtering low stock items");
        }
    }
    
    public void filterExpensive(View v) {
        try {
            if (dataBarang == null) {
                dataBarang = new ArrayList<>();
            }
            
            dataBarang.clear();
            Cursor cursor = db.filterByPriceRange(100000, Double.MAX_VALUE);
            
            if (cursor != null) {
                processDataCursor(cursor);
                cursor.close();
            }
            
            updateUI();
            showToast("Showing expensive items (≥100k)");
            
        } catch (Exception e) {
            Log.e(TAG, "Error in filterExpensive", e);
            showToast("Error filtering expensive items");
        }
    }
    
    public void testDatabase(View v) {
        try {
            int totalItems = db.getTotalCount();
            String message = "Database Status:\n" +
                           "📊 Total Items: " + totalItems + "\n" +
                           "💾 Database: " + (db != null ? "Connected" : "Disconnected") + "\n" +
                           "🔄 Last Update: " + getCurrentTimestamp();
            
            new AlertDialog.Builder(this)
                .setTitle("Database Test")
                .setMessage(message)
                .setIcon(R.drawable.ic_database)
                .setPositiveButton("OK", null)
                .show();
                
        } catch (Exception e) {
            Log.e(TAG, "Error in testDatabase", e);
            showToast("Error testing database: " + e.getMessage());
        }
    }
    
    public void addSampleData(View v) {
        try {
            // Add sample data for testing
            String[][] sampleData = {
                {"Laptop ASUS", "10", "8500000"},
                {"Mouse Gaming", "25", "150000"},
                {"Keyboard Mechanical", "15", "750000"},
                {"Monitor 24 inch", "8", "2500000"},
                {"Webcam HD", "30", "350000"},
                {"Headset Gaming", "20", "450000"},
                {"SSD 1TB", "12", "1200000"},
                {"RAM 16GB", "18", "950000"},
                {"Processor Intel i5", "5", "3500000"},
                {"Graphics Card RTX", "3", "12000000"}
            };
            
            int successCount = 0;
            
            for (String[] item : sampleData) {
                long result = db.insertBarang(item[0], Integer.parseInt(item[1]), Double.parseDouble(item[2]));
                if (result != -1) {
                    successCount++;
                }
            }
            
            refreshData();
            showToast("Added " + successCount + " sample items");
            
        } catch (Exception e) {
            Log.e(TAG, "Error in addSampleData", e);
            showToast("Error adding sample data: " + e.getMessage());
        }
    }
    
    // Utility methods
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    private void showErrorDialog(String title, String message) {
        try {
            new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
        } catch (Exception e) {
            Log.e(TAG, "Error showing error dialog", e);
        }
    }
    
    // Lifecycle methods
    @Override
    protected void onResume() {
        super.onResume();
        try {
            Log.d(TAG, "onResume called");
            refreshData();
        } catch (Exception e) {
            Log.e(TAG, "Error in onResume", e);
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        try {
            Log.d(TAG, "onPause called");
            // Save current search query
            if (sharedPreferences != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("last_search", currentSearchQuery);
                editor.apply();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onPause", e);
        }
    }
    
    @Override
    protected void onDestroy() {
        try {
            Log.d(TAG, "onDestroy called");
            
            // Close database connection
            if (db != null) {
                db.close();
                db = null;
            }
            
            // Clear references
            if (dataBarang != null) {
                dataBarang.clear();
                dataBarang = null;
            }
            
            adapter = null;
            
        } catch (Exception e) {
            Log.e(TAG, "Error in onDestroy", e);
        } finally {
            super.onDestroy();
        }
    }
    
    @Override
    public void onBackPressed() {
        try {
            if (isUpdateMode) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Keluar dari mode update?");
                builder.setMessage("Perubahan yang belum disimpan akan hilang.");
                
                builder.setPositiveButton("Ya", (dialog, which) -> {
                    exitUpdateMode();
                    clearForm();
                });
                
                builder.setNegativeButton("Tidak", null);
                builder.show();
                
            } else {
                super.onBackPressed();
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error in onBackPressed", e);
            super.onBackPressed();
        }
    }
}
