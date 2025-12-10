package com.kelas.wavesoffood.data.local.dao

import androidx.room.*
import com.kelas.wavesoffood.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>
    
    @Query("SELECT * FROM cart_items WHERE id = :cartItemId")
    suspend fun getCartItemById(cartItemId: String): CartItem?
    
    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemCount(): Flow<Int>
    
    @Query("SELECT SUM(totalPrice) FROM cart_items")
    fun getCartTotal(): Flow<Double?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)
    
    @Update
    suspend fun updateCartItem(cartItem: CartItem)
    
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)
    
    @Query("DELETE FROM cart_items WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: String)
    
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
