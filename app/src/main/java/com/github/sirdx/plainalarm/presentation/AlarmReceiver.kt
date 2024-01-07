package com.github.sirdx.plainalarm.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
                            // TODO: Display an UI
                        }

                        Log.i("ALARM", alarm.name)
                        alarmScheduler.schedule(alarm, nextDay = true)
                    }
                }.launchIn(this)
        }
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
    }
}
