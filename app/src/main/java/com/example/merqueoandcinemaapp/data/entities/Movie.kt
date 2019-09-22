package com.example.merqueoandcinemaapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "overview") val overview: String?
    //@ColumnInfo(name = "genre") val genre: List<Int>?
)