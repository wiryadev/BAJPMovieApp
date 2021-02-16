package com.wiryadev.bajpmovie.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity

class FavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getWatchlistMovie(sort: String): LiveData<PagedList<MovieEntity>> = movieRepository.getWatchlistMovie(sort)

    fun getWatchlistTv(sort: String): LiveData<PagedList<TvEntity>> = movieRepository.getWatchlistTv(sort)

}