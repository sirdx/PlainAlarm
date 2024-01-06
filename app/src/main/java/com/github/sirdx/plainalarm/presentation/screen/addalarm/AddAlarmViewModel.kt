package com.github.sirdx.plainalarm.presentation.screen.addalarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.domain.model.AlarmId
import com.github.sirdx.plainalarm.domain.usecase.alarm.UpsertAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddAlarmViewModel @Inject constructor(
    private val upsertAlarmUseCase: UpsertAlarmUseCase
) : ViewModel() {

    private val _addedAlarmId: MutableStateFlow<AlarmId?> = MutableStateFlow(null)
    val addedAlarmId: StateFlow<AlarmId?> = _addedAlarmId.asStateFlow()

    private val _canSubmit = MutableStateFlow(false)
    val canSubmit: StateFlow<Boolean> = _canSubmit.asStateFlow()

    private val _formName = MutableStateFlow("")
    val formName: StateFlow<String> = _formName.asStateFlow()

    private val _formTime = MutableStateFlow(LocalTime(0, 0))
    val formTime: StateFlow<LocalTime> = _formTime.asStateFlow()

    private fun validateForm() {
        var canSubmit = true

        if (formName.value.isBlank()) {
            canSubmit = false
        }

        _canSubmit.update {
            canSubmit
        }
    }

    fun onNameChange(name: String) {
        _formName.update {
            name
        }

        validateForm()
    }

    fun onTimeChange(time: LocalTime) {
        _formTime.update {
            time
        }

        validateForm()
    }

    fun addAlarm() = viewModelScope.launch(Dispatchers.IO) {
        val alarm = Alarm(
            name = formName.value.trim(),
            time = formTime.value
        )

        upsertAlarmUseCase(alarm)
            .onEach { alarmId ->
                _addedAlarmId.update {
                    alarmId
                }
            }.launchIn(this)
    }
}
