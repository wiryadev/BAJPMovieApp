package com.wiryadev.bajpmovie.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.vo.Resource

class TvViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun discoverTv(): LiveData<Resource<PagedList<TvEntity>>> = movieRepository.discoverTv()

}