package com.bashirli.watchme.model.firebase.user

data class UserModel(
    val status:Boolean,
    val data: UserData?,
    val message:String?
)
