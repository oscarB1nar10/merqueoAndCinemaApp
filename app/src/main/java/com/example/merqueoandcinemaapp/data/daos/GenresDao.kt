package com.example.merqueoandcinemaapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.merqueoandcinemaapp.data.entities.Genres

@Dao
interface GenresDao{

    @Query("SELECT * FROM Genres WHERE uid =(:uid)")
    fun findById(uid: Int): Genres

    @Insert
    fun insertAll(vararg genres: Genres)

}