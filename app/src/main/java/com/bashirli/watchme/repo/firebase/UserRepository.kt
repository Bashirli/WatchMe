package com.bashirli.watchme.repo.firebase

import androidx.lifecycle.MutableLiveData
import com.bashirli.watchme.model.firebase.user.UserData
import com.bashirli.watchme.model.firebase.user.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth:FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepo {

    private val _userModel=MutableLiveData<UserModel?>()

    override suspend fun getUserData(): UserModel? {
        return try {
            val data=firestore.collection("UserData").document(auth.currentUser!!.email!!).get().await()
            val email=data.get("email") as String
            val pp=data.get("profilePicture") as String
            val nickname=data.get("nickname") as String
               _userModel.value= UserModel(true,
               UserData(nickname,email,pp),null
               )
            _userModel.value
        }catch (e:Exception){
            _userModel.value=UserModel(false,null,e.localizedMessage)
            _userModel.value
        }
    }
}