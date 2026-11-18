package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("categoryId"), Index("serverId")]
)
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val categoryId: Long? = null,

    val name: String,

    val description: String? = null,

    val salePrice: Double,

    val imageUrl: String? = null,

    val isAvailable: Boolean = true,

    val sortOrder: Int = 0
)