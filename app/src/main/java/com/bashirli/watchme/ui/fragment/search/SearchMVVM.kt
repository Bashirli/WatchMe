package com.bashirli.watchme.ui.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.genre.list.GenreModel
import com.bashirli.watchme.model.movie.MovieModel
import com.bashirli.watchme.repo.api.data.ApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMVVM @Inject constructor(
    private val repo: ApiRepo
) : ViewModel() {

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading

    private val _genres=MutableLiveData<Resource<GenreModel>?>()
    val genres:LiveData<Resource<GenreModel>?> get()=_genres

    private val _movieData=MutableLiveData<Resource<MovieModel>?>()
    val movieData:LiveData<Resource<MovieModel>?> get()=_movieData

    init {
        loadData()
    }


    private fun loadData(){
        loadGenres()
        loadMoviePage(1)
    }

    private fun loadGenres(){
        _genres.value=null
        _loading.value=true
        viewModelScope.launch {
            val response=repo.getGenres()
            _genres.value=response
            _loading.value=false
        }
    }

    fun loadMoviePage(page:Int){
        _movieData.value=null
        _loading.value=true
        viewModelScope.launch {
            val response=repo.getMovies(page)
            _movieData.value=response
            _loading.value=false
        }
    }

    fun searchMovie(searchText:String,page:Int){
        _movieData.value=null
        _loading.value=true
        viewModelScope.launch {
            val response=repo.searchMovies(searchText, page)
            _movieData.value=response
            _loading.value=false
        }
    }


}