package com.github.sirdx.plainalarm.domain.model

import kotlinx.datetime.LocalTime

typealias AlarmId = Long

data class Alarm(
    val id: AlarmId = 0L,
    val name: String = "",
    val time: LocalTime = LocalTime(0, 0),
    val isEnabled: Boolean = false
)
