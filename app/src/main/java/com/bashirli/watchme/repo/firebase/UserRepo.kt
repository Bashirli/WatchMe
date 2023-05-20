package com.bashirli.watchme.repo.firebase

import com.bashirli.watchme.model.firebase.user.UserModel

interface UserRepo {

    suspend fun getUserData():UserModel?

}