package com.wiryadev.bajpmovie.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity

data class TvResponse(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<TvEntity>,

    @field:SerializedName("total_results")
    val totalResults: Int
)