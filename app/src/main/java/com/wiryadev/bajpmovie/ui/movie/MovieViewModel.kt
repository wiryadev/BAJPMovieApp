package com.wiryadev.bajpmovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.vo.Resource

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

//    var movies: MutableLiveData<Resource<PagedList<MovieEntity>>>? = null
//        get() {
//            if (field == null) {
//                field = MutableLiveData<Resource<PagedList<MovieEntity>>>()
//                discoverMovies()
//            }
//            return field
//        }
//        private set
//
//    private fun discoverMovies() {
//        movies = movieRepository.discoverMovie() as MutableLiveData<Resource<PagedList<MovieEntity>>>
//    }

    var movies: LiveData<Resource<PagedList<MovieEntity>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                discoverMovies()
            }
            return field
        }

    private fun discoverMovies() {
        movies = movieRepository.discoverMovie() as MutableLiveData<Resource<PagedList<MovieEntity>>>
    }

}