package com.github.sirdx.plainalarm.domain.repository

import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.domain.model.AlarmId
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun upsertAlarm(alarm: Alarm): Flow<AlarmId>

    fun deleteAlarm(alarm: Alarm): Flow<Boolean>

    fun getAllAlarms(): Flow<List<Alarm>>

    fun getAlarmById(alarmId: AlarmId): Flow<Alarm?>
}
