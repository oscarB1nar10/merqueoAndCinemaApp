package com.example.merqueoandcinemaapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.merqueoandcinemaapp.model.moviePoko.MovieGeneralResponse
import com.example.merqueoandcinemaapp.repository.MainFragmentRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application){

    init {
        MainFragmentRepository.initDB(application)
    }

    val moviesMutableLiveData = MainFragmentRepository.moviesResponse
    val customerMoviesRent = MainFragmentRepository.customerMoviesRent

    private val handlerExceptions = CoroutineExceptionHandler{ _, exception ->
        val movieGeneralResponse = MovieGeneralResponse()
        movieGeneralResponse.status= false
        movieGeneralResponse.message = exception.message
        moviesMutableLiveData.postValue(movieGeneralResponse)
    }



    fun getMovies(){

        viewModelScope.launch(Dispatchers.Unconfined) {
            val job = GlobalScope.launch(handlerExceptions + Dispatchers.Unconfined) {
                //MainFragmentRepository?.getGenre()
                MainFragmentRepository?.getPopularMovies()
            }
            job.join()

        }
    }

    fun saveCustomerMovies(id: Int){
        viewModelScope.launch(Dispatchers.Unconfined) {
            val job = GlobalScope.launch(handlerExceptions + Dispatchers.Unconfined) {
                MainFragmentRepository?.saveCustomerMovies(id)
            }
            job.join()

        }
    }

    fun getCustomerMoviesNum(){
        viewModelScope.launch(Dispatchers.Unconfined) {
            val job = GlobalScope.launch(handlerExceptions + Dispatchers.Unconfined) {
                MainFragmentRepository?.getRentCustomerMovies()
            }
            job.join()

        }
    }

    fun delleteAllCustomerMovies(){
        viewModelScope.launch(Dispatchers.Unconfined) {
            val job = GlobalScope.launch(handlerExceptions + Dispatchers.Unconfined) {
                MainFragmentRepository?.delleteAllCustomerMovies()
            }
            job.join()
        }
    }

}