package com.github.sirdx.plainalarm.domain.usecase.alarm

import com.github.sirdx.plainalarm.domain.repository.AlarmRepository

class GetAllAlarmsUseCase(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke() =
        alarmRepository.getAllAlarms()
}
