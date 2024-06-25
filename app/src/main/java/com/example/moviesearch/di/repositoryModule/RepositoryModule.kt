package com.example.moviesearch.di.repositoryModule

import com.example.moviesearch.data.repository.MainRepositoryImpl
import com.example.moviesearch.domain.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMainRepository(mainRepository: MainRepositoryImpl): MainRepository
}