package com.wiryadev.bajpmovie.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity

data class GenreResponse(
	@field:SerializedName("genres")
	val genres: List<GenreEntity>
)
