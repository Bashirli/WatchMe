package com.bashirli.watchme.repo.firebase

import androidx.lifecycle.MutableLiveData
import com.bashirli.watchme.model.firebase.auth.AuthModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth:FirebaseAuth,
    private val firestore:FirebaseFirestore
) : AuthRepo {

    private val _authModel=MutableLiveData<AuthModel>()

    override suspend fun authLogin(
        email: String,
        password: String,
    ): AuthModel? {
      return try {
          auth.signInWithEmailAndPassword(email, password)
              .addOnSuccessListener {
                  _authModel.value= AuthModel(true,null)
          }
              .addOnFailureListener {
              _authModel.value= AuthModel(false,it.localizedMessage)
          }.await()
          _authModel.value
      }catch (e:Exception){
          _authModel.value= AuthModel(false,e.localizedMessage)
          _authModel.value
      }
    }

    override suspend fun authRegister(
        email: String,
        nickname: String,
        password: String,
    ): AuthModel? {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _authModel.value= AuthModel(true,null)
                    val userData= hashMapOf<String,Any>(
                        "email" to email,
                        "nickname" to nickname,
                        "profilePicture" to "null",
                    )
                    firestore.collection("UserData").document(email).set(userData)
                        .addOnFailureListener {
                            _authModel.value= AuthModel(false,it.localizedMessage)
                        }.addOnSuccessListener {
                            _authModel.value= AuthModel(true,null)
                        }
                }
                .addOnFailureListener {
                    _authModel.value= AuthModel(false,it.localizedMessage)
                }.await()
            _authModel.value
        }catch (e:Exception){
            _authModel.value= AuthModel(false,e.localizedMessage)
            _authModel.value
        }

    }

    override suspend fun authGuest(): AuthModel? {
        return try {
            auth.signOut()
            auth.signInAnonymously().addOnFailureListener {
                _authModel.value= AuthModel(false,it.localizedMessage)
            }.addOnSuccessListener {
                _authModel.value= AuthModel(true,null)
            }.await()
            _authModel.value
        }catch (e:Exception){
            _authModel.value= AuthModel(false,e.localizedMessage)
            _authModel.value
        }

    }

    override suspend fun sendPassResetLink(email: String): AuthModel? {
        return try {
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                _authModel.value= AuthModel(true,null)
            }.addOnFailureListener {
                _authModel.value= AuthModel(false,it.localizedMessage)
            }.await()
            _authModel.value
        }catch (e:Exception){
        _authModel.value= AuthModel(false,e.localizedMessage)
        _authModel.value
        }

    }

    override suspend fun signOut(): AuthModel? {
        return try {
            auth.signOut()
            _authModel.value= AuthModel(true,null)
            _authModel.value
        }catch (e:Exception){
            _authModel.value= AuthModel(false,e.localizedMessage)
            _authModel.value
        }
    }

    override fun getToken(): String? {
        auth.currentUser?.let {
          return it.uid
        }?: return null
    }


}