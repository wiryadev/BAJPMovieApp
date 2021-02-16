package com.wiryadev.bajpmovie.di

import android.content.Context
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.LocalDataSource
import com.wiryadev.bajpmovie.data.source.local.room.MovieDb
import com.wiryadev.bajpmovie.data.source.remote.RemoteDataSource
import com.wiryadev.bajpmovie.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDb.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

}