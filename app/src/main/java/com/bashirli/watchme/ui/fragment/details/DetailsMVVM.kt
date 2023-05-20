package com.bashirli.watchme.ui.fragment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.moviedetails.MovieDetails
import com.bashirli.watchme.model.moviedetails.recommendations.MovieRecommendationsResponse
import com.bashirli.watchme.repo.api.data.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMVVM @Inject constructor(
    private val repo: MovieRepo
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading

    private val _movieData=MutableLiveData<Resource<MovieDetails>?>()
    val movieData:LiveData<Resource<MovieDetails>?> get()=_movieData

    private val _recommendData=MutableLiveData<Resource<MovieRecommendationsResponse>?>()
    val recommendData:LiveData<Resource<MovieRecommendationsResponse>?> get()=_recommendData

    fun loadData(movie_id:Int){
        _movieData.value=null
        _loading.value=true
        viewModelScope.launch {
            val response=repo.getMovieDetails(movie_id)
            _movieData.value=response
            _loading.value=false
        }

    }

    fun loadRecommendations(movie_id: Int){
        _recommendData.value=null
        viewModelScope.launch {
            val response=repo.getMovieRecommendations(movie_id)
            _recommendData.value=response
        }

    }


}