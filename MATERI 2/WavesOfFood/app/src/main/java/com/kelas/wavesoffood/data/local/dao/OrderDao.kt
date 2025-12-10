package com.kelas.wavesoffood.data.local.dao

import androidx.room.*
import com.kelas.wavesoffood.data.model.Order
import com.kelas.wavesoffood.data.model.OrderStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    
    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY orderTime DESC")
    fun getOrdersByUser(userId: String): Flow<List<Order>>
    
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: String): Order?
    
    @Query("SELECT * FROM orders WHERE userId = :userId AND status = :status")
    fun getOrdersByStatus(userId: String, status: OrderStatus): Flow<List<Order>>
    
    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY orderTime DESC LIMIT 1")
    suspend fun getLatestOrder(userId: String): Order?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)
    
    @Update
    suspend fun updateOrder(order: Order)
    
    @Delete
    suspend fun deleteOrder(order: Order)
    
    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()
}
