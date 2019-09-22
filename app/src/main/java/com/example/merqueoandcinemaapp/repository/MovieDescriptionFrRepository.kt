package com.example.merqueoandcinemaapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.merqueoandcinemaapp.data.db.MerqueoAndCinemaDB
import com.example.merqueoandcinemaapp.data.entities.Movie
import com.example.merqueoandcinemaapp.model.moviePoko.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MovieDescriptionFrRepository {

    //const
    val TAG: String = "MainFragmentRepository"
    //vars
    val moviesResponse: MutableLiveData<MovieDetail> = MutableLiveData()
    lateinit var db : MerqueoAndCinemaDB


    fun initDB(application: Application){
        db = MerqueoAndCinemaDB.getInstance(application)
    }

    suspend fun getMovieById(id: Int){
        withContext(Dispatchers.IO){
            moviesResponse.postValue(db.movieDao().findById(id).toMovieDetail())
        }

    }

   private  fun Movie.toMovieDetail() : MovieDetail{
        val movieDetail = MovieDetail()
        movieDetail.title = this.title
        movieDetail.posterPath = this.image
        movieDetail.overview = this.overview

        return  movieDetail
    }



}