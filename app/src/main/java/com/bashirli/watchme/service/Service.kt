package com.bashirli.watchme.service


import com.bashirli.watchme.ALL
import com.bashirli.watchme.API_KEY
import com.bashirli.watchme.DISCOVER
import com.bashirli.watchme.GENRE
import com.bashirli.watchme.LIST
import com.bashirli.watchme.MOVIE
import com.bashirli.watchme.RECOMMENDATIONS
import com.bashirli.watchme.Resource
import com.bashirli.watchme.SEARCH
import com.bashirli.watchme.TRENDING
import com.bashirli.watchme.WEEK
import com.bashirli.watchme.model.genre.item.SelectedGenreMovieModel
import com.bashirli.watchme.model.genre.list.GenreModel
import com.bashirli.watchme.model.movie.MovieModel
import com.bashirli.watchme.model.moviedetails.MovieDetails
import com.bashirli.watchme.model.moviedetails.recommendations.MovieRecommendationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET(GENRE+"/"+MOVIE+"/"+LIST)
    suspend fun getGenres(@Query("api_key") api_key:String= API_KEY):Response<GenreModel>

    @GET(LIST+"/"+"{list_id}")
    suspend fun getListMovieOfGenre(
        @Path("list_id") list_id:Int,
        @Query("api_key") api_key:String= API_KEY):Response<SelectedGenreMovieModel>

    @GET(DISCOVER+"/"+ MOVIE)
    suspend fun getMovies(
        @Query("page") page:Int?=1,
        @Query("api_key") api_key:String=API_KEY):Response<MovieModel>


    @GET(TRENDING+"/"+"{media_type}"+"/"+"{time_window}")
    suspend fun getTrending(
        @Path("media_type") media_type:String,
        @Path("time_window") time_window:String,
        @Query("page") page:Int,
        @Query("api_key") api_key:String=API_KEY):Response<MovieModel>


    @GET(MOVIE+"/"+"{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String=API_KEY
    ):Response<MovieDetails>


    @GET(MOVIE+"/"+"{movie_id}"+"/"+ RECOMMENDATIONS)
    suspend fun getMovieRecommendations(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String=API_KEY
    ):Response<MovieRecommendationsResponse>


    @GET(SEARCH+"/"+ MOVIE)
    suspend fun searchMovie(
        @Query("query") searchText:String,
        @Query("page") page: Int,
        @Query("api_key") api_key:String=API_KEY
    ):Response<MovieModel>




}