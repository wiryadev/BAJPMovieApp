package com.wiryadev.bajpmovie.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_entities")
    fun discoverMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tv_entities")
    fun discoverTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM genre_entities WHERE type = :type")
    fun getGenre(type: String): LiveData<List<GenreEntity>>

    @Query("SELECT * FROM movie_entities WHERE is_favorite = 1 ORDER BY release_date DESC")
    fun getWatchlistMovieDefault(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE is_favorite = 1 ORDER BY title ASC")
    fun getWatchlistMovieAsc(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE is_favorite = 1 ORDER BY title DESC")
    fun getWatchlistMovieDesc(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tv_entities WHERE is_favorite = 1 ORDER BY first_air_date DESC")
    fun getWatchlistTvDefault(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tv_entities WHERE is_favorite = 1 ORDER BY name ASC")
    fun getWatchlistTvAsc(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tv_entities WHERE is_favorite = 1 ORDER BY name DESC")
    fun getWatchlistTvDesc(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT is_favorite FROM movie_entities WHERE id = :id")
    fun checkFavoriteMovie(id: Int): LiveData<Boolean>

    @Query("SELECT is_favorite FROM tv_entities WHERE id = :id")
    fun checkFavoriteTv(id: Int): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreMovie(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreTv(genres: List<GenreEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTv(tv: TvEntity)
}