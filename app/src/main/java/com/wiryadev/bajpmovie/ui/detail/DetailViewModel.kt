package com.wiryadev.bajpmovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.vo.Resource

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getGenreMovie(): LiveData<Resource<List<GenreEntity>>> = movieRepository.getGenreMovie()

    fun getGenreTv(): LiveData<Resource<List<GenreEntity>>> = movieRepository.getGenreTv()

    fun checkFavoriteMovie(id: Int): LiveData<Boolean> = movieRepository.checkFavoriteMovie(id)

    fun checkFavoriteTv(id: Int): LiveData<Boolean> = movieRepository.checkFavoriteTv(id)

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        movieRepository.setFavoriteMovie(movie, state)
    }

    fun setFavoriteTv(tv: TvEntity, state: Boolean) {
        movieRepository.setFavoriteTv(tv, state)
    }

}