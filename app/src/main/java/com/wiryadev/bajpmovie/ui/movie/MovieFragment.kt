package com.wiryadev.bajpmovie.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.databinding.FragmentMovieBinding
import com.wiryadev.bajpmovie.ui.adapter.MovieAdapter
import com.wiryadev.bajpmovie.ui.detail.DetailActivity
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_MOVIE
import com.wiryadev.bajpmovie.utils.invisible
import com.wiryadev.bajpmovie.utils.visible
import com.wiryadev.bajpmovie.viewmodel.ViewModelFactory
import com.wiryadev.bajpmovie.vo.Status

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val movieAdapter = MovieAdapter()

            movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MovieEntity) {
                    goToDetail(data)
                }
            })

            binding.rvMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
                setHasFixedSize(true)
            }

            viewModel.movies?.observe(viewLifecycleOwner, {
                it?.let {
                    when (it.status) {
                        Status.LOADING -> binding.progressBar.visible()
                        Status.SUCCESS -> {
                            binding.progressBar.invisible()
                            movieAdapter.submitList(it.data)
                        }
                        Status.ERROR -> {
                            binding.progressBar.visible()
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToDetail(movie: MovieEntity) {
        Toast.makeText(requireContext(), movie.title, Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, movie)
        intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE_MOVIE)
        startActivity(intent)
    }

}