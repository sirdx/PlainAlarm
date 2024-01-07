package com.github.sirdx.plainalarm.data.repository

import com.github.sirdx.plainalarm.data.source.local.db.dao.AlarmDao
import com.github.sirdx.plainalarm.data.source.local.db.entity.AlarmEntity
import com.github.sirdx.plainalarm.data.source.local.scheduler.AlarmScheduler
import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.domain.model.AlarmId
import com.github.sirdx.plainalarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmScheduler: AlarmScheduler
) : AlarmRepository {

    override fun upsertAlarm(alarm: Alarm) = flow {
        val id = alarmDao.upsertAlarm(AlarmEntity.fromAlarm(alarm))
        emit(id)
    }

    override fun deleteAlarm(alarm: Alarm) = flow {
        if (alarm.isEnabled) {
            alarmScheduler.cancel(alarm)
        }

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

    override fun toggleAlarm(alarm: Alarm) = flow {
        val isEnabled = alarm.isEnabled

        if (isEnabled) {
            alarmScheduler.cancel(alarm)
        } else {
            alarmScheduler.schedule(alarm)
        }

        alarmDao.upsertAlarm(AlarmEntity.fromAlarm(alarm.copy(
            isEnabled = !isEnabled
        )))

        emit(Unit)
    }
}
