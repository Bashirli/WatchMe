package com.bashirli.watchme.ui.fragment.trending

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
class TrendingMVVM @Inject constructor(
        private val repo: ApiRepo
) : ViewModel() {

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading

    private val _movieData=MutableLiveData<Resource<MovieModel>>()
    val movieData:LiveData<Resource<MovieModel>> get()=_movieData

    private val _movieUpdateData=MutableLiveData<Resource<MovieModel>?>()
    val movieUpdateData:LiveData<Resource<MovieModel>?> get()=_movieUpdateData

    fun loadMovies(page:Int){
        _loading.value=true
        viewModelScope.launch {
            val response=repo.getTrending(page)
            _movieData.value=response
            _loading.value=false
        }
    }

    fun updateList(page: Int){
        _movieUpdateData.value=null
        viewModelScope.launch {
            val response=repo.getTrending(page)
            _movieUpdateData.value=response
        }
    }


}