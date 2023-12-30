package com.github.sirdx.plainalarm.data.repository

import com.github.sirdx.plainalarm.data.source.local.db.dao.AlarmDao
import com.github.sirdx.plainalarm.data.source.local.db.entity.AlarmEntity
import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.domain.model.AlarmId
import com.github.sirdx.plainalarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : AlarmRepository {

    override fun upsertAlarm(alarm: Alarm) = flow {
        val id = alarmDao.upsertAlarm(AlarmEntity.fromAlarm(alarm))
        emit(id)
    }

    override fun deleteAlarm(alarm: Alarm) = flow {
        val affectedRecords = alarmDao.deleteAlarmById(alarm.id)
        emit(affectedRecords > 0)
    }

    override fun getAllAlarms() = flow {
        val alarms = alarmDao.getAllAlarms().map { it.toAlarm() }
        emit(alarms)
    }

    override fun getAlarmById(alarmId: AlarmId) = flow {
        val alarm = alarmDao.getAlarmById(alarmId)?.toAlarm()
        emit(alarm)
    }
}
