package com.github.sirdx.plainalarm.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sirdx.plainalarm.core.Resource
import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.domain.model.AlarmId
import com.github.sirdx.plainalarm.domain.usecase.alarm.DeleteAlarmUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.GetAlarmByIdUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.GetAllAlarmsUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.ToggleAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val toggleAlarmUseCase: ToggleAlarmUseCase
) : ViewModel() {

    private val _alarms: MutableStateFlow<Resource<List<Alarm>>> = MutableStateFlow(Resource.Loading)
    val alarms: StateFlow<Resource<List<Alarm>>> = _alarms.asStateFlow()

    init {
        loadAlarms()
    }

    private fun loadAlarms() {
        _alarms.update {
            Resource.Loading
        }

        getAllAlarmsUseCase()
            .onEach { alarms ->
                _alarms.update {
                    Resource.Success(alarms)
                }
            }
            .catch { cause ->
                _alarms.update {
                    Resource.Error(cause.message)
                }
            }.launchIn(viewModelScope)
    }

    fun toggleAlarm(alarm: Alarm, toggle: Boolean) {
        toggleAlarmUseCase(alarm)
            .onEach {
                _alarms.update { currentState ->
                    if (currentState !is Resource.Success) {
                        return@onEach
                    }

                    Resource.Success(
                        currentState.data.map {
                            if (it.id != alarm.id) {
                                return@map it
                            }

                            it.copy(
                                isEnabled = toggle
                            )
                        }
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun addAlarm(alarmId: AlarmId) {
        getAlarmByIdUseCase(alarmId)
            .onEach {
                it?.let { alarm ->
                    _alarms.update { currentState ->
                        if (currentState !is Resource.Success) {
                            return@onEach
                        }

                        Resource.Success(
                            currentState.data + alarm
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}
