package com.example.merqueoandcinemaapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofi2Class {

    const val BASE_URL = "https://api.themoviedb.org/"
    const val  API_KEY = "1dff4d4476e05951c252e71b052ce549"


    val retrofitBuilder: Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: Services by lazy{
        retrofitBuilder
            .build()
            .create(Services::class.java)
    }

}
