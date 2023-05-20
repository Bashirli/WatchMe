package com.bashirli.watchme.ui.fragment.profile.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.watchme.model.firebase.auth.AuthModel
import com.bashirli.watchme.repo.firebase.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterMVVM @Inject constructor(

    private val repo:AuthRepo
) : ViewModel() {

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading



    private val _authData=MutableLiveData<AuthModel?>()
    val authData:LiveData<AuthModel?> get()=_authData

    fun register(email:String,nickname:String,password:String){
        _loading.value=true
        _authData.value=null
        viewModelScope.launch {
            val response=repo.authRegister(email, nickname, password)
            _authData.value=response
            _loading.value=false
        }
    }

    fun getToken():String?{
        return repo.getToken()
    }





}