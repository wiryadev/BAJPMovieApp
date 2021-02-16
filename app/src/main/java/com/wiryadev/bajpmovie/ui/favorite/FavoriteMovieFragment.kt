package com.wiryadev.bajpmovie.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryadev.bajpmovie.R
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.databinding.FragmentFavoriteMovieBinding
import com.wiryadev.bajpmovie.ui.adapter.MovieAdapter
import com.wiryadev.bajpmovie.ui.detail.DetailActivity
import com.wiryadev.bajpmovie.utils.Constants.Companion.ASCENDING
import com.wiryadev.bajpmovie.utils.Constants.Companion.DEFAULT
import com.wiryadev.bajpmovie.utils.Constants.Companion.DESCENDING
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_MOVIE
import com.wiryadev.bajpmovie.utils.invisible
import com.wiryadev.bajpmovie.viewmodel.ViewModelFactory

class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        initUi()

        val customList = listOf(DEFAULT, ASCENDING, DESCENDING)
        val customAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            customList
        )

        with(binding.movieSpinner) {
            adapter = customAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    showList(customList[0])
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    showList(parent?.getItemAtPosition(position).toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        movieAdapter = MovieAdapter()

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
    }

    private fun showList(sort: String) {
        viewModel.getWatchlistMovie(sort).observe(this, {
            binding.progressBar.invisible()
            movieAdapter.submitList(it)
        })
    }

    private fun goToDetail(movie: MovieEntity) {
        Toast.makeText(requireContext(), movie.title, Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, movie)
        intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE_MOVIE)
        startActivity(intent)
    }

}