package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "restaurant_tables",
    foreignKeys = [
        ForeignKey(
            entity = FloorEntity::class,
            parentColumns = ["id"],
            childColumns = ["floorId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("floorId"), Index("serverId")]
)
data class RestaurantTableEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val name: String,

    val tableType: String = "Dine-in",

    val capacity: Int = 4,

    val sortOrder: Int = 0,

    val isActive: Boolean = true,

    val floorId: Long? = null
)