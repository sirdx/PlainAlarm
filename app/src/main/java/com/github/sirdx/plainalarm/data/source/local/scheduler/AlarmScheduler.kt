package com.github.sirdx.plainalarm.data.source.local.scheduler

import com.github.sirdx.plainalarm.domain.model.Alarm

interface AlarmScheduler {

    fun schedule(alarm: Alarm, nextDay: Boolean = false)
    fun cancel(alarm: Alarm)
}
