package com.example.merqueoandcinemaapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.merqueoandcinemaapp.model.moviePoko.MovieDetail
import com.example.merqueoandcinemaapp.repository.MovieDescriptionFrRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDescriptionFrViewModel(application: Application) : AndroidViewModel(application){



    init {
        MovieDescriptionFrRepository.initDB(application)
    }

    val moviesMutableLiveData = MovieDescriptionFrRepository.moviesResponse

    private val handlerExceptions = CoroutineExceptionHandler{ _, exception ->
       val movieDetail = MovieDetail()
        movieDetail.status= false
        movieDetail.message = exception.message
        moviesMutableLiveData.postValue(movieDetail)
    }



    fun getMovie(id: Int){

        viewModelScope.launch(Dispatchers.Unconfined) {
            val job = GlobalScope.launch(handlerExceptions + Dispatchers.Unconfined) {
                //MainFragmentRepository?.getGenre()
                MovieDescriptionFrRepository.getMovieById(id)
            }
            job.join()

        }
    }

}