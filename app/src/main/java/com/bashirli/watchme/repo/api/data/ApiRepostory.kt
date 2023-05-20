package com.bashirli.watchme.repo.api.data

import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.genre.item.SelectedGenreMovieModel
import com.bashirli.watchme.model.genre.list.GenreModel
import com.bashirli.watchme.model.movie.MovieModel
import com.bashirli.watchme.service.Service
import javax.inject.Inject

class ApiRepostory @Inject constructor(
    private val service: Service
) : ApiRepo {
    override suspend fun getGenres(): Resource<GenreModel> {
        return try{
            val response=service.getGenres()
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getListMovieOfGenre(list_id: Int): Resource<SelectedGenreMovieModel> {
        return try{
            val response=service.getListMovieOfGenre(list_id)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getMovies(page:Int): Resource<MovieModel> {
        return try{
            val response=service.getMovies(page)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getTrending(page: Int): Resource<MovieModel> {
        return try{
            val response=service.getTrending("all","week",page)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun searchMovies(searchText: String, page: Int): Resource<MovieModel> {
        return try{
            val response=service.searchMovie(searchText, page)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }
}