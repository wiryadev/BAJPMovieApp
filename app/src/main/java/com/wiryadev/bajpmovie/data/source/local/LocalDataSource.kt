package com.wiryadev.bajpmovie.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.data.source.local.room.MovieDao
import com.wiryadev.bajpmovie.utils.Constants.Companion.ASCENDING
import com.wiryadev.bajpmovie.utils.Constants.Companion.DEFAULT
import com.wiryadev.bajpmovie.utils.Constants.Companion.DESCENDING
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_MOVIE
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_TV

class LocalDataSource private constructor(private val dao: MovieDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(dao: MovieDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(dao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun discoverMovie(): DataSource.Factory<Int, MovieEntity> = dao.discoverMovie()

    fun discoverTv(): DataSource.Factory<Int, TvEntity> = dao.discoverTv()


    fun getWatchlistMovie(sort: String): DataSource.Factory<Int, MovieEntity> {
        return when(sort) {
            DEFAULT -> dao.getWatchlistMovieDefault()
            ASCENDING -> dao.getWatchlistMovieAsc()
            DESCENDING -> dao.getWatchlistMovieDesc()
            else -> dao.getWatchlistMovieDefault()
        }
    }

    fun getWatchlistTv(sort: String): DataSource.Factory<Int, TvEntity> {
        return when(sort) {
            DEFAULT -> dao.getWatchlistTvDefault()
            ASCENDING -> dao.getWatchlistTvAsc()
            DESCENDING -> dao.getWatchlistTvDesc()
            else -> dao.getWatchlistTvDefault()
        }
    }

    fun getGenreMovie(): LiveData<List<GenreEntity>> = dao.getGenre(TYPE_MOVIE)

    fun getGenreTv(): LiveData<List<GenreEntity>> = dao.getGenre(TYPE_TV)

    fun insertMovies(movies: List<MovieEntity>) {
        dao.insertMovies(movies)
    }

    fun insertTvShows(tvShows: List<TvEntity>) {
        dao.insertTvShows(tvShows)
    }

    fun insertGenreMovie(movieGenres: List<GenreEntity>) {
        dao.insertGenreMovie(movieGenres)
    }

    fun insertGenreTv(tvGenres: List<GenreEntity>) {
        dao.insertGenreTv(tvGenres)
    }

    fun checkFavoriteMovie(id: Int): LiveData<Boolean> = dao.checkFavoriteMovie(id)

    fun checkFavoriteTv(id: Int): LiveData<Boolean> = dao.checkFavoriteTv(id)

    fun setFavoriteMovie(movie: MovieEntity,newState: Boolean) {
        movie.isFavorite = newState
        dao.updateMovie(movie)
    }

    fun setFavoriteTv(tv: TvEntity,newState: Boolean) {
        tv.isFavorite = newState
        dao.updateTv(tv)
    }

}