package com.wiryadev.bajpmovie.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.utils.Constants.Companion.DEFAULT
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var tvPagedList: PagedList<TvEntity>

    @Mock
    private lateinit var movieObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var tvObserver: Observer<PagedList<TvEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun getWatchlistMovie() {
        val dummy = moviePagedList
        `when`(dummy.size).thenReturn(10)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummy

        `when`(repository.getWatchlistMovie(DEFAULT)).thenReturn(movies)
        val movieEntities = viewModel.getWatchlistMovie(DEFAULT).value
        verify(repository).getWatchlistMovie(DEFAULT)
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        viewModel.getWatchlistMovie(DEFAULT).observeForever(movieObserver)
        verify(movieObserver).onChanged(dummy)
    }

    @Test
    fun getWatchlistTv() {
        val dummy = tvPagedList
        `when`(dummy.size).thenReturn(10)
        val tvShows = MutableLiveData<PagedList<TvEntity>>()
        tvShows.value = dummy

        `when`(repository.getWatchlistTv(DEFAULT)).thenReturn(tvShows)
        val tvEntities = viewModel.getWatchlistTv(DEFAULT).value
        verify(repository).getWatchlistTv(DEFAULT)
        assertNotNull(tvEntities)
        assertEquals(10, tvEntities?.size)

        viewModel.getWatchlistTv(DEFAULT).observeForever(tvObserver)
        verify(tvObserver).onChanged(dummy)
    }

}