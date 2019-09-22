package com.example.merqueoandcinemaapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.merqueoandcinemaapp.data.db.MerqueoAndCinemaDB
import com.example.merqueoandcinemaapp.data.entities.Movie
import com.example.merqueoandcinemaapp.model.moviePoko.MovieDetail
import com.example.merqueoandcinemaapp.model.moviePoko.MovieGeneralResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CarShoppingFragmentRepository {

    //vars
    val moviesResponse: MutableLiveData<MovieGeneralResponse> = MutableLiveData()
    lateinit var db : MerqueoAndCinemaDB

    fun initDB(application: Application){
        db = MerqueoAndCinemaDB.getInstance(application)
    }

    suspend fun getCustomerMovies(){
        withContext(Dispatchers.IO){
            val customerMovies = db.customerMovieDao().getAll()
           var movieDetail = ArrayList<MovieDetail>()
            val movieGeneralResponse = MovieGeneralResponse()
            if(customerMovies.isNotEmpty()){
                for(customerMovie in customerMovies){
                    movieDetail.add( db.movieDao().findById(customerMovie.movieUid!!).toMovieDetail())
                }
                movieGeneralResponse.status = true
                movieGeneralResponse.message = "rent movies"
                movieGeneralResponse.results = movieDetail
                moviesResponse.postValue(movieGeneralResponse)
            }else{

                movieGeneralResponse.status = false
                movieGeneralResponse.message = "no rent movies"
                moviesResponse.postValue(movieGeneralResponse)
            }

        }
    }

    suspend fun deleteRentMovieFromDb(uid: Int){
        withContext(Dispatchers.IO){
            db.customerMovieDao().delete(555+uid)
            getCustomerMovies()
        }
    }

    private fun Movie.toMovieDetail() : MovieDetail {
        val movieDetail = MovieDetail()
        movieDetail.id = this.uid
        movieDetail.title = this.title
        movieDetail.posterPath = this.image
        movieDetail.overview = this.overview

        return  movieDetail
    }
}