package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "floors")
data class FloorEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val name: String,

    val sortOrder: Int = 0,

    val isActive: Boolean = true
)