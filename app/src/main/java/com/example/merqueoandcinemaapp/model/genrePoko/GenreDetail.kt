package com.example.merqueoandcinemaapp.model.genrePoko

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GenreDetail {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}