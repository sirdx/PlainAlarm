package com.github.sirdx.plainalarm.data.source.local.db

import androidx.room.TypeConverter
import kotlinx.datetime.LocalTime

object Converters {

    @TypeConverter
    fun fromTime(value: String?) =
        value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun toTime(time: LocalTime?) =
        time?.toString()
}
