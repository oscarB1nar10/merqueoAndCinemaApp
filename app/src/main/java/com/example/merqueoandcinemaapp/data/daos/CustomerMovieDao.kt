package com.example.merqueoandcinemaapp.data.daos

import androidx.room.*
import com.example.merqueoandcinemaapp.data.entities.CustumerMovie

@Dao
interface CustomerMovieDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerMovie(custumerMovie: CustumerMovie)
    @Query("SELECT * FROM CustomerMovie")
    fun getAll(): List<CustumerMovie>
    @Query("DELETE  FROM CustomerMovie WHERE uid = (:uid)")
    fun delete(uid: Int)
    @Query("DELETE  FROM CustomerMovie")
    fun deleteAll()
}