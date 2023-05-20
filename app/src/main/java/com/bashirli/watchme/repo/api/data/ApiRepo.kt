package com.bashirli.watchme.repo.api.data

import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.genre.item.SelectedGenreMovieModel
import com.bashirli.watchme.model.genre.list.GenreModel
import com.bashirli.watchme.model.movie.MovieModel

interface ApiRepo {

    suspend fun getGenres():Resource<GenreModel>

    suspend fun getListMovieOfGenre(list_id : Int):Resource<SelectedGenreMovieModel>

    suspend fun getMovies(page:Int):Resource<MovieModel>

    suspend fun getTrending(page:Int):Resource<MovieModel>

    suspend fun searchMovies(searchText:String,page: Int):Resource<MovieModel>


}