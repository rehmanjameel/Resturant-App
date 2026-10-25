package com.arconn.resturant.roomdb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "halls",
    indices = [Index(value = ["hallNumber"], unique = true)]
)
data class Hall(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hallNumber: Int
)

@Entity(
    tableName = "restaurant_tables",
    foreignKeys = [
        ForeignKey(
            entity = Hall::class,
            parentColumns = ["id"],
            childColumns = ["hallId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["hallId", "tableNumber"], unique = true)
    ]
)
data class RestaurantTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hallId: Int,
    val tableNumber: Int
)

@Entity(
    tableName = "restaurant_orders",
    foreignKeys = [
        ForeignKey(
            entity = RestaurantTable::class,
            parentColumns = ["id"],
            childColumns = ["tableId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("tableId")]
)
data class RestaurantOrder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tableId: Int,
    val status: String = "OPEN",
    val createdAt: Long = System.currentTimeMillis(),
    val closedAt: Long? = null
)

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = RestaurantOrder::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["orderId", "menuItemId"], unique = true)
    ]
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val menuItemId: Int,
    val name: String,
    val unitPrice: Int,
    val quantity: Int
)
