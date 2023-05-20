package com.bashirli.watchme.repo.api.data

import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.moviedetails.MovieDetails
import com.bashirli.watchme.model.moviedetails.recommendations.MovieRecommendationsResponse
import com.bashirli.watchme.service.Service
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service:Service
) : MovieRepo {

    override suspend fun getMovieDetails(movie_id: Int): Resource<MovieDetails> {
        return try{
            val response=service.getMovieDetails(movie_id)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getMovieRecommendations(movie_id: Int) : Resource<MovieRecommendationsResponse> {
        return try{
            val response=service.getMovieRecommendations(movie_id)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

}