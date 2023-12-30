package com.github.sirdx.plainalarm.data.source.local.scheduler

import com.github.sirdx.plainalarm.domain.model.Alarm

interface AlarmScheduler {

    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}
