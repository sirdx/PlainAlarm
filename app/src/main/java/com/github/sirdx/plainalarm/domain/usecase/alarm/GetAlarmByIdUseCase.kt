package com.github.sirdx.plainalarm.domain.usecase.alarm

import com.github.sirdx.plainalarm.domain.model.AlarmId
import com.github.sirdx.plainalarm.domain.repository.AlarmRepository

class GetAlarmByIdUseCase(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(id: AlarmId) =
        alarmRepository.getAlarmById(id)
}
