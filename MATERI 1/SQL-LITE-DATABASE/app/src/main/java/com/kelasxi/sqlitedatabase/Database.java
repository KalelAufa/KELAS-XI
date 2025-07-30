package com.kelasxi.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    
    private static final String TAG = "Database";
    private static final String DATABASE_NAME = "BarangDatabase";
    private static final int DATABASE_VERSION = 5;
    
    // Table and column names
    public static final String TABLE_BARANG = "tblbarang";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BARANG = "barang";
    public static final String COLUMN_STOK = "stok";
    public static final String COLUMN_HARGA = "harga";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";
    
    private SQLiteDatabase db;
    
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Database initialized: " + DATABASE_NAME + " version " + DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating database tables");
        
        // Create enhanced tblbarang table with timestamps
        String createTableQuery = "CREATE TABLE " + TABLE_BARANG + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BARANG + " TEXT NOT NULL, " +
                COLUMN_STOK + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_HARGA + " REAL NOT NULL DEFAULT 0.0, " +
                COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        
        try {
            db.execSQL(createTableQuery);
            
            // Create indexes for better performance
            db.execSQL("CREATE INDEX idx_barang_name ON " + TABLE_BARANG + "(" + COLUMN_BARANG + ")");
            db.execSQL("CREATE INDEX idx_barang_stok ON " + TABLE_BARANG + "(" + COLUMN_STOK + ")");
            db.execSQL("CREATE INDEX idx_barang_harga ON " + TABLE_BARANG + "(" + COLUMN_HARGA + ")");
            
            Log.d(TAG, "Database tables created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error creating database tables", e);
            throw e;
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        
        try {
            // For development, drop and recreate
            // In production, you should implement proper migration
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARANG);
            onCreate(db);
            Log.d(TAG, "Database upgrade completed");
        } catch (Exception e) {
            Log.e(TAG, "Error upgrading database", e);
            throw e;
        }
    }
    
    // Get writable database instance
    private SQLiteDatabase getWritableDb() {
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }
        return db;
    }
    
    // Insert new item using ContentValues (SQL injection safe)
    public long insertBarang(String barang, int stok, double harga) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BARANG, barang);
            values.put(COLUMN_STOK, stok);
            values.put(COLUMN_HARGA, harga);
            values.put(COLUMN_CREATED_AT, getCurrentTimestamp());
            values.put(COLUMN_UPDATED_AT, getCurrentTimestamp());
            
            long result = database.insert(TABLE_BARANG, null, values);
            
            if (result != -1) {
                Log.d(TAG, "Item inserted successfully with ID: " + result);
            } else {
                Log.e(TAG, "Failed to insert item");
            }
            
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting item", e);
            return -1;
        }
    }
    
    // Update existing item using ContentValues (SQL injection safe)
    public boolean updateBarang(String id, String barang, int stok, double harga) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BARANG, barang);
            values.put(COLUMN_STOK, stok);
            values.put(COLUMN_HARGA, harga);
            values.put(COLUMN_UPDATED_AT, getCurrentTimestamp());
            
            String whereClause = COLUMN_ID + " = ?";
            String[] whereArgs = {id};
            
            int rowsUpdated = database.update(TABLE_BARANG, values, whereClause, whereArgs);
            
            boolean success = rowsUpdated > 0;
            if (success) {
                Log.d(TAG, "Item updated successfully, ID: " + id);
            } else {
                Log.w(TAG, "No rows updated for ID: " + id);
            }
            
            return success;
        } catch (Exception e) {
            Log.e(TAG, "Error updating item with ID: " + id, e);
            return false;
        }
    }
    
    // Delete item using prepared statement (SQL injection safe)
    public boolean deleteBarang(String id) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String whereClause = COLUMN_ID + " = ?";
            String[] whereArgs = {id};
            
            int rowsDeleted = database.delete(TABLE_BARANG, whereClause, whereArgs);
            
            boolean success = rowsDeleted > 0;
            if (success) {
                Log.d(TAG, "Item deleted successfully, ID: " + id);
            } else {
                Log.w(TAG, "No rows deleted for ID: " + id);
            }
            
            return success;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting item with ID: " + id, e);
            return false;
        }
    }
    
    
    // Method to execute SQL queries with result handling (for backward compatibility)
    @Deprecated
    public boolean runSQL(String sql) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            Log.w(TAG, "Using deprecated runSQL method. Consider using safer alternatives.");
            database.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error executing SQL: " + sql, e);
            return false;
        }
    }
    
    // Method to execute SELECT queries and return Cursor
    public Cursor selectAll() {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String sql = "SELECT * FROM " + TABLE_BARANG + " ORDER BY " + COLUMN_BARANG + " ASC";
            Cursor cursor = database.rawQuery(sql, null);
            Log.d(TAG, "Selected all records, count: " + (cursor != null ? cursor.getCount() : 0));
            return cursor;
        } catch (Exception e) {
            Log.e(TAG, "Error selecting all records", e);
            return null;
        }
    }
    
    // Method to execute SELECT queries with WHERE clause (SQL injection safe)
    public Cursor selectWhere(String whereClause, String[] whereArgs) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String sql = "SELECT * FROM " + TABLE_BARANG + " WHERE " + whereClause + " ORDER BY " + COLUMN_BARANG + " ASC";
            Cursor cursor = database.rawQuery(sql, whereArgs);
            Log.d(TAG, "Selected with WHERE clause, count: " + (cursor != null ? cursor.getCount() : 0));
            return cursor;
        } catch (Exception e) {
            Log.e(TAG, "Error selecting with WHERE clause: " + whereClause, e);
            return null;
        }
    }
    
    // Method to search by item name (SQL injection safe)
    public Cursor searchByName(String itemName) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String sql = "SELECT * FROM " + TABLE_BARANG + " WHERE " + COLUMN_BARANG + " LIKE ? ORDER BY " + COLUMN_BARANG + " ASC";
            String[] args = {"%" + itemName + "%"};
            Cursor cursor = database.rawQuery(sql, args);
            Log.d(TAG, "Search by name '" + itemName + "', results: " + (cursor != null ? cursor.getCount() : 0));
            return cursor;
        } catch (Exception e) {
            Log.e(TAG, "Error searching by name: " + itemName, e);
            return null;
        }
    }
    
    // Method to filter by stock range (SQL injection safe)
    public Cursor filterByStockRange(int minStock, int maxStock) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String sql = "SELECT * FROM " + TABLE_BARANG + " WHERE " + COLUMN_STOK + " >= ? AND " + COLUMN_STOK + " <= ? ORDER BY " + COLUMN_STOK + " ASC";
            String[] args = {String.valueOf(minStock), String.valueOf(maxStock)};
            Cursor cursor = database.rawQuery(sql, args);
            Log.d(TAG, "Filter by stock range " + minStock + "-" + maxStock + ", results: " + (cursor != null ? cursor.getCount() : 0));
            return cursor;
        } catch (Exception e) {
            Log.e(TAG, "Error filtering by stock range: " + minStock + "-" + maxStock, e);
            return null;
        }
    }
    
    // Method to filter by price range (SQL injection safe)
    public Cursor filterByPriceRange(double minPrice, double maxPrice) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String sql = "SELECT * FROM " + TABLE_BARANG + " WHERE " + COLUMN_HARGA + " >= ? AND " + COLUMN_HARGA + " <= ? ORDER BY " + COLUMN_HARGA + " ASC";
            String[] args = {String.valueOf(minPrice), String.valueOf(maxPrice)};
            Cursor cursor = database.rawQuery(sql, args);
            Log.d(TAG, "Filter by price range " + minPrice + "-" + maxPrice + ", results: " + (cursor != null ? cursor.getCount() : 0));
            return cursor;
        } catch (Exception e) {
            Log.e(TAG, "Error filtering by price range: " + minPrice + "-" + maxPrice, e);
            return null;
        }
    }
    
    // Get single item by ID
    public Cursor getBarangById(String id) {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String sql = "SELECT * FROM " + TABLE_BARANG + " WHERE " + COLUMN_ID + " = ?";
            String[] args = {id};
            Cursor cursor = database.rawQuery(sql, args);
            Log.d(TAG, "Get item by ID " + id + ", found: " + (cursor != null && cursor.getCount() > 0));
            return cursor;
        } catch (Exception e) {
            Log.e(TAG, "Error getting item by ID: " + id, e);
            return null;
        }
    }
    
    // Get total count of items
    public int getTotalCount() {
        SQLiteDatabase database = getWritableDb();
        
        try {
            String sql = "SELECT COUNT(*) FROM " + TABLE_BARANG;
            Cursor cursor = database.rawQuery(sql, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                Log.d(TAG, "Total items count: " + count);
                return count;
            }
            
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "Error getting total count", e);
            return 0;
        }
    }
    
    // Helper method to get current timestamp
    private String getCurrentTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
    
    // Close database connection
    @Override
    public void close() {
        try {
            if (db != null && db.isOpen()) {
                db.close();
                Log.d(TAG, "Database connection closed");
            }
            super.close();
        } catch (Exception e) {
            Log.e(TAG, "Error closing database", e);
        }
    }
    
    // Legacy method for backward compatibility
    @Deprecated
    public Cursor select(String SQL) {
        Log.w(TAG, "Using deprecated select method. Consider using selectAll() or other specific methods.");
        SQLiteDatabase database = getWritableDb();
        
        try {
            return database.rawQuery(SQL, null);
        } catch (Exception e) {
            Log.e(TAG, "Error executing raw SQL: " + SQL, e);
            return null;
        }
    }
}
