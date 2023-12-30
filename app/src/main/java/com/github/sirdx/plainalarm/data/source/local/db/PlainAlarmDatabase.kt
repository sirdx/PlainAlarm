package com.github.sirdx.plainalarm.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.sirdx.plainalarm.data.source.local.db.dao.AlarmDao
import com.github.sirdx.plainalarm.data.source.local.db.entity.AlarmEntity

@Database(
    entities = [
        AlarmEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PlainAlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao
}
