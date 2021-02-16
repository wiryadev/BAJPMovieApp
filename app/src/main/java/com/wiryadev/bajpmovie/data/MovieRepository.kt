package com.wiryadev.bajpmovie.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wiryadev.bajpmovie.data.source.local.LocalDataSource
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.data.source.remote.ApiResponse
import com.wiryadev.bajpmovie.data.source.remote.RemoteDataSource
import com.wiryadev.bajpmovie.data.source.remote.response.GenreResponse
import com.wiryadev.bajpmovie.data.source.remote.response.MovieResponse
import com.wiryadev.bajpmovie.data.source.remote.response.TvResponse
import com.wiryadev.bajpmovie.utils.AppExecutors
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_MOVIE
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_TV
import com.wiryadev.bajpmovie.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): MovieDataSource {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(10)
        .setPageSize(10)
        .build()

    override fun discoverMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        Log.d("Repository", "discoverMovie: called")
        return object : NetworkBoundResource<PagedList<MovieEntity>, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                return LivePagedListBuilder(localDataSource.discoverMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {
                Log.d("Repository", "discoverMovie: createCall")
                return remoteDataSource.discoverMovie()
            }

            override fun saveCallResult(data: MovieResponse) {
                localDataSource.insertMovies(data.results)
            }
        }.asLiveData()
    }

    override fun discoverTv(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, TvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                return LivePagedListBuilder(localDataSource.discoverTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean = data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<TvResponse>> = remoteDataSource.discoverTv()

            override fun saveCallResult(data: TvResponse) {
                localDataSource.insertTvShows(data.results)
            }
        }.asLiveData()
    }

    override fun getGenreMovie(): LiveData<Resource<List<GenreEntity>>> {
        return object : NetworkBoundResource<List<GenreEntity>, GenreResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<GenreEntity>> = localDataSource.getGenreMovie()

            override fun shouldFetch(data: List<GenreEntity>?): Boolean = data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<GenreResponse>> = remoteDataSource.getGenreMovie()

            override fun saveCallResult(data: GenreResponse) {
                val movieGenres = ArrayList<GenreEntity>()
                for (genre in data.genres) {
                    genre.type = TYPE_MOVIE
                    movieGenres.add(genre)
                }
                localDataSource.insertGenreMovie(movieGenres)
            }
        }.asLiveData()
    }

    override fun getWatchlistMovie(sort: String): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(localDataSource.getWatchlistMovie(sort), config).build()
    }

    override fun getWatchlistTv(sort: String): LiveData<PagedList<TvEntity>> {
        return LivePagedListBuilder(localDataSource.getWatchlistTv(sort), config).build()
    }

    override fun getGenreTv(): LiveData<Resource<List<GenreEntity>>> {
        return object : NetworkBoundResource<List<GenreEntity>, GenreResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<GenreEntity>> = localDataSource.getGenreTv()

            override fun shouldFetch(data: List<GenreEntity>?): Boolean = data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<GenreResponse>> = remoteDataSource.getGenreTv()

            override fun saveCallResult(data: GenreResponse) {
                val tvGenres = ArrayList<GenreEntity>()
                for (genre in data.genres) {
                    genre.type = TYPE_TV
                    tvGenres.add(genre)
                }
                localDataSource.insertGenreTv(tvGenres)
            }
        }.asLiveData()
    }

    override fun checkFavoriteMovie(id: Int): LiveData<Boolean> = localDataSource.checkFavoriteMovie(id)

    override fun checkFavoriteTv(id: Int): LiveData<Boolean> = localDataSource.checkFavoriteTv(id)

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
    }

    override fun setFavoriteTv(tv: TvEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTv(tv, state) }
    }

}