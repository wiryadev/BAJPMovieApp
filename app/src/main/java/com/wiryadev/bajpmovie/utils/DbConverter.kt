package com.wiryadev.bajpmovie.utils

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DbConverter {

    @TypeConverter
    fun fromList(genreIds: List<Int>): String = Json.encodeToString(genreIds)

    @TypeConverter
    fun toList(genreIdString: String): List<Int> = Json.decodeFromString(genreIdString)

}