package com.github.sirdx.plainalarm.data.source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.sirdx.plainalarm.domain.model.Alarm
import kotlinx.datetime.LocalTime

@Entity(
    tableName = AlarmEntity.TABLE_NAME
)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val name: String,

    val time: LocalTime,

    val isEnabled: Boolean = false
) {

    fun toAlarm() = Alarm(
        id = id,
        name = name,
        time = time,
        isEnabled = isEnabled
    )

    companion object {
        const val TABLE_NAME = "alarms"

        fun fromAlarm(alarm: Alarm) = AlarmEntity(
            id = alarm.id,
            name = alarm.name,
            time = alarm.time,
            isEnabled = alarm.isEnabled
        )
    }
}
