package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "categories", indices = [Index("serverId")])
data class CategoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val name: String,

    val description: String? = null,

    val imageUrl: String? = null,

    val sortOrder: Int = 0,

    val isActive: Boolean = true
)