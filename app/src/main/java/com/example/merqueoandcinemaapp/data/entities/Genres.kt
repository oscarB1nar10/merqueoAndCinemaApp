package com.example.merqueoandcinemaapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Genres")
data class Genres(
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name = "name") val name: String?
)