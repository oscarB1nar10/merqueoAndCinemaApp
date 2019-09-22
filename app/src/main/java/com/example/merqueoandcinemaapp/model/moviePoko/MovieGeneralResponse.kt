package com.example.merqueoandcinemaapp.model.moviePoko

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MovieGeneralResponse {

    var status: Boolean? = null
    var message: String? = null
    @SerializedName("results")
    @Expose
    var results: ArrayList<MovieDetail>? = null

}