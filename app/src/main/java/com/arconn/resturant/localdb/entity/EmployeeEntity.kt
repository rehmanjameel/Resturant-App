package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "employees", indices = [Index("serverId")])
data class EmployeeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val serverId: Long? = null,

    val name: String,

    val phone: String? = null,

    val address: String? = null,

    val role: String = "Waiter",

    val assignedFloorId: Long? = null,

    val isActive: Boolean = true
)