package com.wiryadev.bajpmovie.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun discoverMovie() {
        val dummy = Resource.success(pagedList)
        `when`(dummy.data?.size).thenReturn(10)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummy

        `when`(repository.discoverMovie()).thenReturn(movies)
        val movieEntities = viewModel.movies?.value?.data

        verify(repository).discoverMovie()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        viewModel.movies?.observeForever(observer)
        verify(observer).onChanged(dummy)
    }

//    @Test
//    fun discoverMovie() {
//        val dummy = Resource.success(pagedList)
//        `when`(dummy.data?.size).thenReturn(10)
//        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
//        movies.value = dummy
//
//        `when`(repository.discoverMovie()).thenReturn(movies)
//        val movieEntities = viewModel.discoverMovies().value?.data
//
//        verify(repository).discoverMovie()
//        assertNotNull(movieEntities)
//        assertEquals(10, movieEntities?.size)
//
//        viewModel.discoverMovies().observeForever(observer)
//        verify(observer).onChanged(dummy)
//    }

}