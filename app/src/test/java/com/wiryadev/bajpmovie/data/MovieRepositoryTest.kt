package com.wiryadev.bajpmovie.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.wiryadev.bajpmovie.data.source.local.LocalDataSource
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.data.source.remote.RemoteDataSource
import com.wiryadev.bajpmovie.utils.AppExecutors
import com.wiryadev.bajpmovie.utils.Constants.Companion.DEFAULT
import com.wiryadev.bajpmovie.utils.DummyData
import com.wiryadev.bajpmovie.utils.LiveDataTestUtil
import com.wiryadev.bajpmovie.utils.PagedListUtil
import com.wiryadev.bajpmovie.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponse = DummyData.generateMovies()
    private val tvResponse = DummyData.generateSeries()
    private val movieGenreResponse = DummyData.generateMovieGenre()
    private val tvGenreResponse = DummyData.generateTvGenre()

    private val movieWatchlist = DummyData.generateMovieWatchlist(movieResponse)
    private val tvWatchlist = DummyData.generateTvWatchlist(tvResponse)

    @Test
    fun discoverMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.discoverMovie()).thenReturn(dataSourceFactory)
        movieRepository.discoverMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DummyData.generateMovies()))

        verify(local).discoverMovie()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size, movieEntities.data?.size)
    }

    @Test
    fun discoverTv() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.discoverTv()).thenReturn(dataSourceFactory)
        movieRepository.discoverTv()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DummyData.generateSeries()))

        verify(local).discoverTv()
        assertNotNull(tvEntities.data)
        assertEquals(tvResponse.size, tvEntities.data?.size)
    }

    @Test
    fun getWatchlistMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        val dummy = DummyData.generateMovieWatchlist(movieResponse)
        `when`(local.getWatchlistMovie(DEFAULT)).thenReturn(dataSourceFactory)
        movieRepository.getWatchlistMovie(DEFAULT)

        val entities = PagedListUtil.mockPagedList(dummy)

        verify(local).getWatchlistMovie(DEFAULT)
        assertNotNull(entities)
        assertEquals(movieWatchlist.size.toLong(), entities.size.toLong())
    }

    @Test
    fun getWatchlistTv() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        val dummy = DummyData.generateTvWatchlist(tvResponse)
        `when`(local.getWatchlistTv(DEFAULT)).thenReturn(dataSourceFactory)
        movieRepository.getWatchlistTv(DEFAULT)

        val tvEntities = PagedListUtil.mockPagedList(dummy)

        verify(local).getWatchlistTv(DEFAULT)
        assertNotNull(tvEntities)
        assertEquals(tvWatchlist.size, tvEntities.size)
    }

    @Test
    fun getGenreMovie() {
        val dummy = MutableLiveData<List<GenreEntity>>()
        dummy.value = DummyData.generateMovieGenre()
        `when`(local.getGenreMovie()).thenReturn(dummy)

        val movieGenreEntities = LiveDataTestUtil.getValue(movieRepository.getGenreMovie())

        verify(local).getGenreMovie()
        assertNotNull(movieGenreEntities.data)
        assertEquals(movieGenreResponse.size, movieGenreEntities.data?.size)
    }

    @Test
    fun getGenreTv() {
        val dummy = MutableLiveData<List<GenreEntity>>()
        dummy.value = DummyData.generateTvGenre()
        `when`(local.getGenreTv()).thenReturn(dummy)

        val tvGenreEntities = LiveDataTestUtil.getValue(movieRepository.getGenreTv())

        verify(local).getGenreTv()
        assertNotNull(tvGenreEntities.data)
        assertEquals(tvGenreResponse.size, tvGenreEntities.data?.size)
    }

    @Test
    fun getFavStateMovie() {
        val dummy = MutableLiveData<Boolean>()
        dummy.value = false
        `when`(local.checkFavoriteMovie(movieResponse[0].id)).thenReturn(dummy)

        val state = LiveDataTestUtil.getValue(movieRepository.checkFavoriteMovie(movieResponse[0].id))

        verify(local).checkFavoriteMovie(movieResponse[0].id)
        assertNotNull(state)
        assertEquals(dummy.value, state)
    }

    @Test
    fun getFavStateTv() {
        val dummy = MutableLiveData<Boolean>()
        dummy.value = false
        `when`(local.checkFavoriteTv(tvResponse[0].id)).thenReturn(dummy)

        val state = LiveDataTestUtil.getValue(movieRepository.checkFavoriteTv(tvResponse[0].id))

        verify(local).checkFavoriteTv(tvResponse[0].id)
        assertNotNull(state)
        assertEquals(dummy.value, state)
    }

}