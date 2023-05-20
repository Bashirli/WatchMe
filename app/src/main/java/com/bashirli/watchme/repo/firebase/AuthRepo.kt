package com.bashirli.watchme.repo.firebase

import com.bashirli.watchme.Resource
import com.bashirli.watchme.model.firebase.auth.AuthModel

interface AuthRepo {

    suspend fun authLogin(email:String,password:String) : AuthModel?

    suspend fun authRegister(email:String,nickname: String,password: String): AuthModel?

    suspend fun authGuest(): AuthModel?

    suspend fun sendPassResetLink(email: String): AuthModel?

    suspend fun signOut(): AuthModel?

    fun getToken():String?

}