package com.wiryadev.bajpmovie.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.vo.Resource

interface MovieDataSource {

    fun discoverMovie(): LiveData<Resource<PagedList<MovieEntity>>>

    fun discoverTv(): LiveData<Resource<PagedList<TvEntity>>>

    fun getWatchlistMovie(sort: String): LiveData<PagedList<MovieEntity>>

    fun getWatchlistTv(sort: String): LiveData<PagedList<TvEntity>>

    fun getGenreMovie(): LiveData<Resource<List<GenreEntity>>>

    fun getGenreTv(): LiveData<Resource<List<GenreEntity>>>

    fun checkFavoriteMovie(id: Int): LiveData<Boolean>

    fun checkFavoriteTv(id: Int): LiveData<Boolean>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun setFavoriteTv(tv: TvEntity, state: Boolean)

}