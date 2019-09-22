package com.example.merqueoandcinemaapp.network

import com.example.merqueoandcinemaapp.model.genrePoko.GenreGeneralResponse
import com.example.merqueoandcinemaapp.model.moviePoko.MovieGeneralResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface  Services{

    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") api_key: String): Call<MovieGeneralResponse>

    @GET("3/genre/movie/list")
    fun getGenres(
        @Query("api_key") api_key: String): Call<GenreGeneralResponse>



}