package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = RestaurantTableEntity::class,
            parentColumns = ["id"],
            childColumns = ["tableId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = EmployeeEntity::class,
            parentColumns = ["id"],
            childColumns = ["waiterId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("tableId"),
        Index("waiterId"),
        Index("serverId"),
        Index("syncStatus")
    ]
)
data class RestaurantOrderEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val orderNumber: String,

    val tableId: Long? = null,

    val waiterId: Long? = null,

    val customerName: String? = null,

    val orderType: String = "Dine-in",

    val subtotal: Double = 0.0,

    val discount: Double = 0.0,

    val tax: Double = 0.0,

    val grandTotal: Double = 0.0,

    val paidAmount: Double = 0.0,

    val remainingAmount: Double = 0.0,

    val orderStatus: String = "PENDING",

    val paymentStatus: String = "UNPAID",

    val notes: String? = null,

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis(),

    val syncStatus: String = "PENDING"
)