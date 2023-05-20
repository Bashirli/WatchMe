package com.bashirli.watchme.ui.fragment.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.movie.MovieModel
import com.bashirli.watchme.repo.api.data.ApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverMVVM @Inject constructor(
    private val repo: ApiRepo
) :ViewModel() {

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading


    private val _movieResponse=MutableLiveData<Resource<MovieModel>>()
    val movieResponse:LiveData<Resource<MovieModel>> get()=_movieResponse

    private val _moviePageResponse=MutableLiveData<Resource<MovieModel>>()
    val moviePageResponse:LiveData<Resource<MovieModel>> get()=_moviePageResponse



     fun loadMovies(page: Int){
        _loading.value=true
        viewModelScope.launch {
            val response=repo.getMovies(page)
            _movieResponse.value=response
            _loading.value=false
        }
    }

     fun sendLoadMovies(page:Int){
        viewModelScope.launch {
            val response=repo.getMovies(page)
            _moviePageResponse.value=response
        }
    }

}