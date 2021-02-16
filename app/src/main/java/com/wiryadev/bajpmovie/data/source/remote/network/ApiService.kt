package com.wiryadev.bajpmovie.data.source.remote.network

import com.wiryadev.bajpmovie.BuildConfig
import com.wiryadev.bajpmovie.data.source.remote.response.GenreResponse
import com.wiryadev.bajpmovie.data.source.remote.response.MovieResponse
import com.wiryadev.bajpmovie.data.source.remote.response.TvResponse
import com.wiryadev.bajpmovie.utils.Constants.Companion.LANGUAGE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    fun discoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("with_original_language") language: String = LANGUAGE
    ): Call<MovieResponse>

    @GET("discover/tv")
    fun discoverTv(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("with_original_language") language: String = LANGUAGE
    ): Call<TvResponse>

    @GET("genre/movie/list")
    fun getGenreMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<GenreResponse>

    @GET("genre/tv/list")
    fun getGenreTv(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<GenreResponse>

}