package com.example.merqueoandcinemaapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.merqueoandcinemaapp.repository.CarShoppingFragmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CarShoppingFragmentViewModel(application: Application) : AndroidViewModel(application){

    init {
        CarShoppingFragmentRepository.initDB(application)
    }

    val moviesMutableLiveData = CarShoppingFragmentRepository.moviesResponse


    fun getCustomerMovies(){
        GlobalScope.launch(Dispatchers.Unconfined) {
            val job = launch {
                CarShoppingFragmentRepository.getCustomerMovies()
            }
           job.join()
        }

    }

    fun deleteRentMovieFromdb(uid: Int){

        GlobalScope.launch(Dispatchers.Unconfined) {
            val job = launch {
                CarShoppingFragmentRepository.deleteRentMovieFromDb(uid)
            }
            job.join()
        }
    }

}