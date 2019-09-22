package com.example.merqueoandcinemaapp.model.moviePoko

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MovieDetail {
    var status: Boolean? = null
    var message: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null

    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass){
            return false
        }

        other as MovieDetail

        if(id != other.id){
            return false
        }
        if(title != other.title){
            return false
        }
        if(posterPath != other.posterPath){
            return false
        }
        if(overview != other.overview){
            return false
        }
        return true
    }
}