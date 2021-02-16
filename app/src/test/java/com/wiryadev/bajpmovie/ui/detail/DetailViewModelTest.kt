package com.wiryadev.bajpmovie.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wiryadev.bajpmovie.data.MovieRepository
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.utils.DummyData
import com.wiryadev.bajpmovie.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<GenreEntity>>>

    @Mock
    private lateinit var stateObserver: Observer<Boolean>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun testGetGenreMovie() {
        val dummy = Resource.success(DummyData.generateMovieGenre())
        val genres = MutableLiveData<Resource<List<GenreEntity>>>()
        genres.value = dummy

        `when`(repository.getGenreMovie()).thenReturn(genres)

        viewModel.getGenreMovie().observeForever(observer)
        verify(observer).onChanged(dummy)
    }

    @Test
    fun testGetGenreTv() {
        val dummy = Resource.success(DummyData.generateTvGenre())
        val genres = MutableLiveData<Resource<List<GenreEntity>>>()
        genres.value = dummy

        `when`(repository.getGenreTv()).thenReturn(genres)

        viewModel.getGenreTv().observeForever(observer)
        verify(observer).onChanged(dummy)
    }

    @Test
    fun getFavStateMovie() {
        val dummy = DummyData.generateMovies()
        val dummyState = dummy[0].isFavorite
        val state = MutableLiveData<Boolean>()
        state.value = dummy[0].isFavorite

        `when`(repository.checkFavoriteMovie(dummy[0].id)).thenReturn(state)

        viewModel.checkFavoriteMovie(dummy[0].id).observeForever(stateObserver)
        verify(stateObserver).onChanged(dummyState)
    }

    @Test
    fun getFavStateTv() {
        val dummy = DummyData.generateSeries()
        val dummyState = dummy[0].isFavorite
        val state = MutableLiveData<Boolean>()
        state.value = dummy[0].isFavorite

        `when`(repository.checkFavoriteTv(dummy[0].id)).thenReturn(state)

        viewModel.checkFavoriteTv(dummy[0].id).observeForever(stateObserver)
        verify(stateObserver).onChanged(dummyState)
    }

}