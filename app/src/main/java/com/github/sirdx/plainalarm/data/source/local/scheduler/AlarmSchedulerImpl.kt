package com.github.sirdx.plainalarm.data.source.local.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.presentation.AlarmReceiver
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.EXTRA_MESSAGE, alarm.name)
        }

        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.todayIn(timeZone)
        val alarmDateTime = alarm.time.atDate(today)
        val alarmInstant = alarmDateTime.toInstant(timeZone)
        val alarmMillis = alarmInstant.toEpochMilliseconds()

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmMillis,
            DAY_MILLIS,
            PendingIntent.getBroadcast(
                context,
                alarm.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarm: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.id.toInt(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
    
    companion object {
        private const val DAY_MILLIS = 24L * 60L * 60L * 60L * 1000L
    }
}
