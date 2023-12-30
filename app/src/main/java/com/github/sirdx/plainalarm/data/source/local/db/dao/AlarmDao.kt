package com.github.sirdx.plainalarm.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.sirdx.plainalarm.data.source.local.db.entity.AlarmEntity

@Dao
interface AlarmDao {

    @Upsert
    suspend fun upsertAlarm(alarm: AlarmEntity): Long

    @Query("DELETE FROM ${AlarmEntity.TABLE_NAME} " +
            "WHERE id = :id")
    suspend fun deleteAlarmById(id: Long): Int

    @Query("SELECT * FROM ${AlarmEntity.TABLE_NAME}")
    suspend fun getAllAlarms(): List<AlarmEntity>

    @Query("SELECT * FROM ${AlarmEntity.TABLE_NAME} " +
            "WHERE id = :id LIMIT 1")
    suspend fun getAlarmById(id: Long): AlarmEntity?
}
