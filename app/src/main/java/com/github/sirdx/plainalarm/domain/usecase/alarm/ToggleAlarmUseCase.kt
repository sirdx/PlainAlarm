package com.github.sirdx.plainalarm.domain.usecase.alarm

import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.domain.repository.AlarmRepository

class ToggleAlarmUseCase(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(alarm: Alarm) =
        alarmRepository.toggleAlarm(alarm)
}
