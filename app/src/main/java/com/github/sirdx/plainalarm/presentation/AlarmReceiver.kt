package com.github.sirdx.plainalarm.presentation

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.sirdx.plainalarm.PlainAlarmApplication
import com.github.sirdx.plainalarm.R
import com.github.sirdx.plainalarm.data.source.local.scheduler.AlarmScheduler
import com.github.sirdx.plainalarm.domain.usecase.alarm.GetAlarmByIdUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var getAlarmByIdUseCase: GetAlarmByIdUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmId = intent?.getLongExtra(EXTRA_ID, 0) ?: return

        runBlocking {
            getAlarmByIdUseCase(alarmId)
                .onEach {
                    it?.let { alarm ->
                        context?.let { ctx ->
                            val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            val lockScreenIntent = Intent(ctx, LockScreenActivity::class.java).apply {
                                putExtra(LockScreenActivity.EXTRA_NAME, alarm.name)
                            }
                            val pendingIntent = PendingIntent.getActivity(ctx, 0, lockScreenIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

                            val builder = NotificationCompat.Builder(ctx, PlainAlarmApplication.NOTIFICATION_CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Alarm")
                                .setContentText(alarm.name)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setFullScreenIntent(pendingIntent, true)

                            notificationManager.notify(1, builder.build())
                        }

                        alarmScheduler.schedule(alarm, nextDay = true)
                    }
                }.launchIn(this)
        }
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
    }
}
