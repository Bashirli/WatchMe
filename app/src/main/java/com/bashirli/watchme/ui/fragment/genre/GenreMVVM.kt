package com.bashirli.watchme.ui.fragment.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.genre.item.SelectedGenreMovieModel
import com.bashirli.watchme.repo.api.data.ApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreMVVM @Inject constructor(
    private val repo: ApiRepo
) : ViewModel() {

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading

    private val _genreData=MutableLiveData<Resource<SelectedGenreMovieModel>?>()
    val genreData:LiveData<Resource<SelectedGenreMovieModel>?> get()=_genreData


    fun loadData(genreId:Int){
        _loading.value=true
        _genreData.value=null
        viewModelScope.launch {
            val response=repo.getListMovieOfGenre(genreId)
            _genreData.value=response
            _loading.value=false
        }
    }



}