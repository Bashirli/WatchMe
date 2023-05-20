package com.bashirli.watchme.model.moviedetails.recommendations


import com.google.gson.annotations.SerializedName

data class MovieRecommendationsResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)