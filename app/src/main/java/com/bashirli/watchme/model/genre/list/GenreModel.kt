package com.bashirli.watchme.model.genre.list


import com.google.gson.annotations.SerializedName

data class GenreModel(
    @SerializedName("genres")
    val genres: List<Genre>
)