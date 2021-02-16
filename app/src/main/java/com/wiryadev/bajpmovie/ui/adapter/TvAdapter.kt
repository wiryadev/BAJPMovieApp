package com.wiryadev.bajpmovie.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.wiryadev.bajpmovie.BuildConfig
import com.wiryadev.bajpmovie.R
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.databinding.ItemListBinding
import com.wiryadev.bajpmovie.utils.Constants.Companion.CORNER_RADIUS
import com.wiryadev.bajpmovie.utils.Constants.Companion.PLACEHOLDER

class TvAdapter : PagedListAdapter<TvEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TvViewHolder {
        val itemListBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        tv?.let { holder.bind(it) }
    }

    inner class TvViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            with(binding) {
                tvTitle.text = tv.name
                tvReleaseDate.text = tv.firstAirDate
                tvOverview.text = tv.overview

                val score = (tv.voteAverage * 10).toInt()
                tvScore.text = root.resources.getString(R.string.score, score.toString())

                val poster = BuildConfig.BASE_IMAGE_URL + tv.posterPath
                Log.d("Movie Adapter", "bind: $poster")
                ivPoster.load(poster) {
                    placeholder(PLACEHOLDER)
                    transformations(RoundedCornersTransformation(CORNER_RADIUS))
                }

                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(tv)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvEntity)
    }
}