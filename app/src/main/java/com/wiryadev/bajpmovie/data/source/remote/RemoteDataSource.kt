package com.wiryadev.bajpmovie.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wiryadev.bajpmovie.data.source.remote.network.ApiConfig
import com.wiryadev.bajpmovie.data.source.remote.response.GenreResponse
import com.wiryadev.bajpmovie.data.source.remote.response.MovieResponse
import com.wiryadev.bajpmovie.data.source.remote.response.TvResponse
import com.wiryadev.bajpmovie.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.net.UnknownHostException

class RemoteDataSource {

    companion object {
        private const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun discoverMovie(): LiveData<ApiResponse<MovieResponse>> {
        Log.d(TAG, "discoverMovie: called")
        EspressoIdlingResource.increment()
        val movieResults = MutableLiveData<ApiResponse<MovieResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            val client = ApiConfig.getApiService().discoverMovie()
            val response = client.awaitResponse()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, it.toString())
                        movieResults.postValue(ApiResponse.success(it))
                    }
                } else {
                    Log.d(TAG, "onResponse: ${response.code()}")
                }
            } catch (e: HttpException) {
                ApiResponse.error(response.message(), response.body())
                Log.d(TAG, response.message())
            } catch (e: UnknownHostException) {
                ApiResponse.error(response.message(), response.body())
            } catch (e: Throwable) {
                Log.d(TAG, "${e.message}")
            }
            EspressoIdlingResource.decrement()
        }
        return movieResults
    }

    fun discoverTv(): LiveData<ApiResponse<TvResponse>> {
        EspressoIdlingResource.increment()
        val tvResults = MutableLiveData<ApiResponse<TvResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            val client = ApiConfig.getApiService().discoverTv()
            val response = client.awaitResponse()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        tvResults.postValue(ApiResponse.success(it))
                    }
                } else {
                    ApiResponse.empty(response.message(), response.body())
                }
            } catch (e: HttpException) {
                ApiResponse.error(response.message(), response.body())
            } catch (e: UnknownHostException) {
                ApiResponse.error(response.message(), response.body())
            } catch (e: Throwable) {
                ApiResponse.error("Something went wrong", null)
            }
            EspressoIdlingResource.decrement()
        }
        return tvResults
    }

    fun getGenreMovie(): LiveData<ApiResponse<GenreResponse>> {
        EspressoIdlingResource.increment()
        val movieGenre = MutableLiveData<ApiResponse<GenreResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            val client = ApiConfig.getApiService().getGenreMovie()
            val response = client.awaitResponse()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        movieGenre.postValue(ApiResponse.success(it))
                    }
                } else {
                    ApiResponse.empty(response.message(), response.body())
                }
            } catch (e: HttpException) {
                ApiResponse.error(response.message(), response.body())
            } catch (e: UnknownHostException) {
                ApiResponse.error(response.message(), response.body())
            } catch (e: Throwable) {
                ApiResponse.error("Something went wrong", null)
            }
            EspressoIdlingResource.decrement()
        }
        return movieGenre
    }

    fun getGenreTv(): LiveData<ApiResponse<GenreResponse>> {
        EspressoIdlingResource.increment()
        val tvGenre = MutableLiveData<ApiResponse<GenreResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            val client = ApiConfig.getApiService().getGenreTv()
            val response = client.awaitResponse()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        tvGenre.postValue(ApiResponse.success(it))
                    }
                } else {
                    ApiResponse.empty(response.message(), response.body())
                }
            } catch (e: HttpException) {
                ApiResponse.error(response.message(), response.body())
            } catch (e: UnknownHostException) {
                ApiResponse.error(response.message(), response.body())
            } catch (e: Throwable) {
                ApiResponse.error("Something went wrong", null)
            }
            EspressoIdlingResource.decrement()
        }
        return tvGenre
    }

}