package com.github.sirdx.plainalarm.presentation.screen.home

import androidx.lifecycle.ViewModel
import com.github.sirdx.plainalarm.domain.usecase.alarm.DeleteAlarmUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.GetAlarmByIdUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.GetAllAlarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : ViewModel() {


}
