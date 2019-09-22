package com.example.merqueoandcinemaapp.model.genrePoko

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GenreGeneralResponse {
    @SerializedName("genres")
    @Expose
    var genres: List<GenreDetail>? = null

}