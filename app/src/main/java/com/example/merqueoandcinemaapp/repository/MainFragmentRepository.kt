package com.example.merqueoandcinemaapp.repository

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import com.example.merqueoandcinemaapp.data.db.MerqueoAndCinemaDB
import com.example.merqueoandcinemaapp.data.entities.CustumerMovie
import com.example.merqueoandcinemaapp.data.entities.Movie
import com.example.merqueoandcinemaapp.model.genrePoko.GenreGeneralResponse
import com.example.merqueoandcinemaapp.model.moviePoko.MovieDetail
import com.example.merqueoandcinemaapp.model.moviePoko.MovieGeneralResponse
import com.example.merqueoandcinemaapp.network.Retrofi2Class
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainFragmentRepository {

    //const
    val TAG: String = "MainFragmentRepository"
    //vars
    val moviesResponse: MutableLiveData<MovieGeneralResponse> = MutableLiveData()
    val customerMoviesRent: MutableLiveData<Int> = MutableLiveData()
    lateinit var db : MerqueoAndCinemaDB
    lateinit var application : Application


    fun initDB(application: Application){
        this.application = application
       db = MerqueoAndCinemaDB.getInstance(application)
    }

    @SuppressLint("MissingPermission")
    suspend fun getPopularMovies() {

        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        //region bad
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        //endregion
        if(isConnected) {

            val call = Retrofi2Class.apiService.getPopularMovies(Retrofi2Class.API_KEY)

            withContext(Dispatchers.IO) {
                withTimeout(5000L) {
                    call.enqueue(object : Callback<MovieGeneralResponse> {

                        override fun onResponse(
                            call: Call<MovieGeneralResponse>,
                            response: Response<MovieGeneralResponse>
                        ) {
                            if (response.code() == 200) {
                                //deleteRentMovies(response.body()?.results!!)
                                moviesResponse.value = response.body()
                                addMoviesToDB(response.body()!!)
                            }
                        }

                        override fun onFailure(call: Call<MovieGeneralResponse>, t: Throwable) {
                            val movieGeneralResponse = MovieGeneralResponse()
                            movieGeneralResponse.status = false
                            movieGeneralResponse.message = t.message
                            moviesResponse.value = movieGeneralResponse
                        }

                    })
                }
            }
        }else{
            withContext(Dispatchers.IO){
                getMoviesFromDB(db.movieDao().getAll())
            }
        }
    }

    //region
    suspend fun getGenre(){

        val call = Retrofi2Class.apiService.getGenres(Retrofi2Class.API_KEY)

        withContext(Dispatchers.IO) {
            withTimeout(5000L) {
                call.enqueue(object : Callback<GenreGeneralResponse>{

                    override fun onResponse(call: Call<GenreGeneralResponse>, response: Response<GenreGeneralResponse>) {
                        if(response != null){

                        }
                    }
                    override fun onFailure(call: Call<GenreGeneralResponse>, t: Throwable) {

                    }

                })
            }
        }
    }
    //endregion

    suspend fun saveCustomerMovies(id: Int){
        withContext(Dispatchers.IO){
            db.customerMovieDao().insertCustomerMovie(CustumerMovie(555+id, 555, id))
            getRentCustomerMovies()
        }
    }

    suspend fun getRentCustomerMovies(){
        withContext(Dispatchers.IO){
            customerMoviesRent.postValue(db.customerMovieDao().getAll().size)
        }
    }

    fun addMoviesToDB(movieGeneralResponse: MovieGeneralResponse){
        GlobalScope.launch(Dispatchers.Unconfined) {
            val job =  launch(Dispatchers.IO) {
                var movieList = ArrayList<Movie>()
                for(movieDetail in movieGeneralResponse.results!!){
                    movieList.add(movieDetail.toMovieEntity())
                }
                db.movieDao().insertAll(movieList)
            }
            job.join()
        }
    }

    suspend fun delleteAllCustomerMovies(){
        withContext(Dispatchers.IO){
            db.customerMovieDao().deleteAll()
        }
    }

    fun MovieDetail.toMovieEntity() = Movie(
        this.id!!, this.title,this.posterPath,this.overview
    )

    private fun getMoviesFromDB(movies: List<Movie>){
        val movieGeneralResponse = MovieGeneralResponse()
        if(movies.isNotEmpty()) {
            var movieList = ArrayList<MovieDetail>()
            for (movie in movies) {
                movieList.add(movie.toMovieDetail())
            }
            movieGeneralResponse.status = true
            movieGeneralResponse.message = "data found"
            movieGeneralResponse.results = movieList
            moviesResponse.postValue(movieGeneralResponse)
        }else{
            movieGeneralResponse.status = false
            movieGeneralResponse.message = "there isn't data"
            moviesResponse.postValue(movieGeneralResponse)
        }

    }

    private fun Movie.toMovieDetail() : MovieDetail{
        val movieDetail = MovieDetail()
        movieDetail.id = this.uid
        movieDetail.title = this.title
        movieDetail.posterPath = this.image
        movieDetail.overview = this.overview

        return  movieDetail
    }

    //region experimental
    private fun deleteRentMovies(movies: List<MovieDetail>){
        val movieGeneralResponse = MovieGeneralResponse()
        var filterMovies = ArrayList<MovieDetail>()
        GlobalScope.launch(Dispatchers.Unconfined) {
            val job = launch(Dispatchers.IO) {

                val rentMovies = db.customerMovieDao().getAll()

                if(rentMovies.isNotEmpty()) {
                    var indexInit = 0
                    var lastIndex: Int = rentMovies.size
                    for (movie in movies) {
                        for (customerMovie in rentMovies) {
                            indexInit++
                            if (movie.id == customerMovie.movieUid) {
                                indexInit = 0
                                break
                            } else if (indexInit == lastIndex ) {
                                filterMovies.add(movie)
                                indexInit = 0
                            }
                        }
                    }
                }else{
                    filterMovies.addAll(movies)
                }
            }
            job.join()

            movieGeneralResponse.status = true
            movieGeneralResponse.message = "data found"
            movieGeneralResponse.results = filterMovies
            moviesResponse.postValue(movieGeneralResponse)

        }

    }
    //endregion



}