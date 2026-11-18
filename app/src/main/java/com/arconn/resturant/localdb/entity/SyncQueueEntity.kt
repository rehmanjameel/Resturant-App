package com.arconn.resturant.localdb.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sync_queue",
    indices = [Index("status")]
)
data class SyncQueueEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val entityType: String,

    val localEntityId: Long,

    val operation: String,

    val payload: String,

    val status: String = "PENDING",

    val retryCount: Int = 0,

    val createdAt: Long = System.currentTimeMillis()
)