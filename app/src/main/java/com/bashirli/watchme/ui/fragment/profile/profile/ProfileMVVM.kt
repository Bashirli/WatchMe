package com.bashirli.watchme.ui.fragment.profile.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.watchme.model.firebase.auth.AuthModel
import com.bashirli.watchme.model.firebase.user.UserModel
import com.bashirli.watchme.repo.firebase.AuthRepo
import com.bashirli.watchme.repo.firebase.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMVVM @Inject constructor(
  //  private val repo:AuthRepo,
  //  private val userRepo:UserRepo

) : ViewModel() {

    private val _loading= MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get()=_loading

/*
    private val _authData= MutableLiveData<AuthModel?>()
    val authData: LiveData<AuthModel?> get()=_authData

    private val _userData= MutableLiveData<UserModel?>()
    val userData: LiveData<UserModel?> get()=_userData

    fun signOut(){
        _loading.value=true
        _authData.value=null
        viewModelScope.launch {
            val response=repo.signOut()
            _authData.value=response
            _loading.value=false
        }
    }

    fun loadData(){
        _loading.value=true
        _userData.value=null
        viewModelScope.launch {
            val response=userRepo.getUserData()
            _userData.value=response
            _loading.value=false
        }
    }


 */
}