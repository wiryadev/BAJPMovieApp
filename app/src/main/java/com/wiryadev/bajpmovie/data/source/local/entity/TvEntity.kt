package com.wiryadev.bajpmovie.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tv_entities")
@Parcelize
data class TvEntity(
	@PrimaryKey
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("overview")
	val overview: String,

	@ColumnInfo(name = "genre_ids")
	@field:SerializedName("genre_ids")
	val genreIds: List<Int>,

	@ColumnInfo(name = "poster_path")
	@field:SerializedName("poster_path")
	val posterPath: String,

	@ColumnInfo(name = "backdrop_path")
	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@ColumnInfo(name = "first_air_date")
	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@ColumnInfo(name = "vote_average")
	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@ColumnInfo(name = "is_favorite")
	var isFavorite: Boolean = false,
) : Parcelable