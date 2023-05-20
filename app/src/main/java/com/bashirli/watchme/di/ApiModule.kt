package com.bashirli.watchme.di

import com.bashirli.watchme.BASE_URL
import com.bashirli.watchme.repo.api.data.ApiRepo
import com.bashirli.watchme.repo.api.data.ApiRepostory
import com.bashirli.watchme.repo.api.data.MovieRepo
import com.bashirli.watchme.repo.api.data.MovieRepository
import com.bashirli.watchme.service.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun injectRetrofit()=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun injectApiService(retrofit: Retrofit)=retrofit.create(Service::class.java)

    @Singleton
    @Provides
    fun injectRepo(service:Service)= ApiRepostory(service) as ApiRepo

    @Singleton
    @Provides
    fun injectMovieRepo(service: Service)= MovieRepository(service) as MovieRepo


}