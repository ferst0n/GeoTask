package com.example.geotask.data.di

import com.example.geotask.data.DataSource.RemoteDataSource
import com.example.geotask.data.api.Service
import com.example.geotask.data.api.retrofit
import com.example.geotask.data.mappers.FromDataToDomainMapper
import com.example.geotask.data.repositoryImpl.PlaceRepositoryImpl
import com.example.geotask.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofite() = retrofit.provideRetrofit()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(Service::class.java)

    @Singleton
    @Provides
    fun providePlaceRepository(remoteDataSource: RemoteDataSource, mapper: FromDataToDomainMapper)=
            PlaceRepositoryImpl(remoteDataSource,mapper) as PlaceRepository

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: Service) =
            RemoteDataSource(service)


}