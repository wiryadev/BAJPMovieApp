package com.wiryadev.bajpmovie.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity

data class MovieResponse(
	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<MovieEntity>,

	@field:SerializedName("total_results")
	val totalResults: Int
)