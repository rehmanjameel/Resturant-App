package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = RestaurantOrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("orderId"),
        Index("productId"),
        Index("serverId")
    ]
)
data class OrderItemEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val orderId: Long,

    val productId: Long? = null,

    val productName: String,

    val quantity: Double,

    val unitPrice: Double,

    val discount: Double = 0.0,

    val total: Double,

    val notes: String? = null
)