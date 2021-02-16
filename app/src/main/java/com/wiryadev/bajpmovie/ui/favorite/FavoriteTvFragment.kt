package com.wiryadev.bajpmovie.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryadev.bajpmovie.R
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.databinding.FragmentFavoriteTvBinding
import com.wiryadev.bajpmovie.ui.adapter.TvAdapter
import com.wiryadev.bajpmovie.ui.detail.DetailActivity
import com.wiryadev.bajpmovie.utils.Constants.Companion.ASCENDING
import com.wiryadev.bajpmovie.utils.Constants.Companion.DEFAULT
import com.wiryadev.bajpmovie.utils.Constants.Companion.DESCENDING
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_TV
import com.wiryadev.bajpmovie.utils.invisible
import com.wiryadev.bajpmovie.viewmodel.ViewModelFactory

class FavoriteTvFragment : Fragment() {

    private var _binding: FragmentFavoriteTvBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        initUi()

        val customList = listOf(DEFAULT, ASCENDING, DESCENDING)
        val customAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            customList
        )

        with(binding.tvSpinner) {
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
        tvAdapter = TvAdapter()

        tvAdapter.setOnItemClickCallback(object : TvAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvEntity) {
                goToDetail(data)
            }
        })

        binding.rvTvShows.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tvAdapter
            setHasFixedSize(true)
        }
    }

    private fun showList(sort: String) {
        viewModel.getWatchlistTv(sort).observe(this, {
            binding.progressBar.invisible()
            tvAdapter.submitList(it)
        })
    }

    private fun goToDetail(tv: TvEntity) {
        Toast.makeText(requireContext(), tv.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, tv)
        intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE_TV)
        startActivity(intent)
    }

}