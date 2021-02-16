package com.wiryadev.bajpmovie.ui.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.databinding.FragmentTvBinding
import com.wiryadev.bajpmovie.ui.adapter.TvAdapter
import com.wiryadev.bajpmovie.ui.detail.DetailActivity
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_TV
import com.wiryadev.bajpmovie.utils.invisible
import com.wiryadev.bajpmovie.utils.visible
import com.wiryadev.bajpmovie.viewmodel.ViewModelFactory
import com.wiryadev.bajpmovie.vo.Status

class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]

            val tvAdapter = TvAdapter()

            tvAdapter.setOnItemClickCallback(object : TvAdapter.OnItemClickCallback {
                override fun onItemClicked(data: TvEntity) {
                    goToDetail(data)
                }
            })

            binding.rvTvShows.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = tvAdapter
                setHasFixedSize(true)
            }

            viewModel.discoverTv().observe(viewLifecycleOwner, {
                it?.let {
                    when (it.status) {
                        Status.LOADING -> binding.progressBar.visible()
                        Status.SUCCESS -> {
                            binding.progressBar.invisible()
                            tvAdapter.submitList(it.data)
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

    private fun goToDetail(tv: TvEntity) {
        Toast.makeText(requireContext(), tv.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, tv)
        intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE_TV)
        startActivity(intent)
    }

}