package com.example.merqueoandcinemaapp.data.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.merqueoandcinemaapp.SingletonHolder
import com.example.merqueoandcinemaapp.data.daos.CustomerMovieDao
import com.example.merqueoandcinemaapp.data.daos.GenresDao
import com.example.merqueoandcinemaapp.data.daos.MoviesDao
import com.example.merqueoandcinemaapp.data.entities.CustumerMovie
import com.example.merqueoandcinemaapp.data.entities.Genres
import com.example.merqueoandcinemaapp.data.entities.Movie

@Database(entities = arrayOf(Genres::class, Movie::class, CustumerMovie::class), version = 1)
abstract class MerqueoAndCinemaDB : RoomDatabase() {

    abstract fun genresDao(): GenresDao
    abstract fun movieDao(): MoviesDao
    abstract fun customerMovieDao() : CustomerMovieDao


    companion object : SingletonHolder<MerqueoAndCinemaDB, Context>({

        Room.databaseBuilder(it.applicationContext,
            MerqueoAndCinemaDB::class.java, "MerqueoAndCinema.db")
            .build()

    })
}