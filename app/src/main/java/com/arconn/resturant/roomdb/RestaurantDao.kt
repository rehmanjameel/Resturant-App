package com.arconn.resturant.roomdb


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Query("SELECT COUNT(*) FROM halls")
    suspend fun countHalls(): Int

    @Query("SELECT COUNT(*) FROM restaurant_tables")
    suspend fun countTables(): Int

    @Query("SELECT * FROM halls ORDER BY hallNumber")
    suspend fun getHallsOnce(): List<Hall>

    @Query("""
    SELECT COUNT(*) FROM restaurant_tables
    WHERE hallId = :hallId
""")
    suspend fun countTablesForHall(hallId: Int): Int

    // Halls
    @Query("SELECT * FROM halls ORDER BY hallNumber")
    fun observeHalls(): Flow<List<Hall>>

    @Query("SELECT COALESCE(MAX(hallNumber), 0) + 1 FROM halls")
    suspend fun nextHallNumber(): Int

    @Insert
    suspend fun insertHall(hall: Hall)

    // Tables
    @Query(
        """
        SELECT * FROM restaurant_tables
        WHERE hallId = :hallId
        ORDER BY tableNumber
    """
    )
    fun observeTables(hallId: Int): Flow<List<RestaurantTable>>

    @Query(
        """
        SELECT COALESCE(MAX(tableNumber), 0) + 1
        FROM restaurant_tables
        WHERE hallId = :hallId
    """
    )
    suspend fun nextTableNumber(hallId: Int): Int

    @Insert
    suspend fun insertTable(table: RestaurantTable)

    @Query(
        """
        SELECT * FROM restaurant_tables
        WHERE hallId = :hallId AND tableNumber = :tableNumber
        LIMIT 1
    """
    )
    suspend fun findTable(
        hallId: Int,
        tableNumber: Int
    ): RestaurantTable?

    // Orders
    @Query(
        """
        SELECT * FROM restaurant_orders
        WHERE tableId = :tableId AND status = 'OPEN'
        ORDER BY id DESC LIMIT 1
    """
    )
    suspend fun findOpenOrder(tableId: Int): RestaurantOrder?

    @Query(
        """
    SELECT * FROM order_items
    WHERE orderId = :orderId
    ORDER BY id
"""
    )
    fun observeOrderItems(orderId: Long): Flow<List<OrderItem>>

    @Insert
    suspend fun insertOrder(order: RestaurantOrder): Long

    @Query(
        """
        UPDATE restaurant_orders
        SET status = 'CLOSED', closedAt = :closedAt
        WHERE id = :orderId AND status = 'OPEN'
    """
    )
    suspend fun closeOrder(
        orderId: Long,
        closedAt: Long = System.currentTimeMillis()
    )

    // Order items
    @Query(
        """
        SELECT * FROM order_items
        WHERE orderId = :orderId AND menuItemId = :menuItemId
        LIMIT 1
    """
    )
    suspend fun findOrderItem(
        orderId: Long,
        menuItemId: Int
    ): OrderItem?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertOrderItem(item: OrderItem)

    @Query(
        """
        UPDATE order_items
        SET quantity = quantity + :quantity
        WHERE id = :itemId
    """
    )
    suspend fun increaseItemQuantity(
        itemId: Long,
        quantity: Int
    )

    @Transaction
    suspend fun addItemToOrder(
        orderId: Long,
        menuItemId: Int,
        name: String,
        price: Int,
        quantity: Int
    ) {
        val existing = findOrderItem(orderId, menuItemId)

        if (existing == null) {
            insertOrderItem(
                OrderItem(
                    orderId = orderId,
                    menuItemId = menuItemId,
                    name = name,
                    unitPrice = price,
                    quantity = quantity
                )
            )
        } else {
            increaseItemQuantity(existing.id, quantity)
        }
    }

    @Query("SELECT * FROM restaurant_orders ORDER BY id DESC")
    fun observeAllOrders(): Flow<List<RestaurantOrder>>
}
