package com.wiryadev.bajpmovie.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.vo.Resource
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
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(repository)
    }

    @Test
    fun discoverTv() {
        val dummy = Resource.success(pagedList)
        `when`(dummy.data?.size).thenReturn(10)
        val tvShows = MutableLiveData<Resource<PagedList<TvEntity>>>()
        tvShows.value = dummy

        `when`(repository.discoverTv()).thenReturn(tvShows)
        val tvEntities = viewModel.discoverTv().value?.data

        verify(repository).discoverTv()
        assertNotNull(tvEntities)
        assertEquals(10, tvEntities?.size)

        viewModel.discoverTv().observeForever(observer)
        verify(observer).onChanged(dummy)
    }
}