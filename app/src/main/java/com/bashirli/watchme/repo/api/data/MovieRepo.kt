package com.bashirli.watchme.repo.api.data

import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.moviedetails.MovieDetails
import com.bashirli.watchme.model.moviedetails.recommendations.MovieRecommendationsResponse

interface MovieRepo {

    suspend fun getMovieDetails(movie_id:Int):Resource<MovieDetails>

    suspend fun getMovieRecommendations(movie_id: Int):Resource<MovieRecommendationsResponse>

}