package com.example.merqueoandcinemaapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CustomerMovie")
data class CustumerMovie(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "clientUid") val clientUid: Int?,
    @ColumnInfo(name = "movieUid") val movieUid: Int?
)