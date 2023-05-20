package com.bashirli.watchme.di.firebase

import com.bashirli.watchme.repo.firebase.AuthRepository
import com.bashirli.watchme.repo.firebase.AuthRepo
import com.bashirli.watchme.repo.firebase.UserRepo
import com.bashirli.watchme.repo.firebase.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object FirebaseModel {

    @Singleton
    @Provides
    fun injectAuth()=FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun injectFirestore()=FirebaseFirestore.getInstance()


    @Singleton
    @Provides
    fun injectAuthRepo(auth: FirebaseAuth,firestore: FirebaseFirestore)=
        AuthRepository(auth,firestore) as AuthRepo

    @Singleton
    @Provides
    fun injectUserRepo(auth: FirebaseAuth,firestore: FirebaseFirestore)=UserRepository(auth, firestore) as UserRepo

}