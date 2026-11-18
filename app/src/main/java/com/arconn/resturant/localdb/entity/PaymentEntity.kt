package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = RestaurantOrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("orderId"), Index("serverId")]
)
data class PaymentEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val orderId: Long,

    val amount: Double,

    val paymentMethod: String,

    val paymentDate: Long = System.currentTimeMillis(),

    val reference: String? = null,

    val syncStatus: String = "PENDING"
)