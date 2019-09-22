package com.example.merqueoandcinemaapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.merqueoandcinemaapp.data.entities.Movie

@Dao
interface MoviesDao{

    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM Movie WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Movie>

    @Query("SELECT * FROM Movie WHERE uid = (:uid)")
    fun findById(uid: Int): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( movies: ArrayList<Movie>)


}