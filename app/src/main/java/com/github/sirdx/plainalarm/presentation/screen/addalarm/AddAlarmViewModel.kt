package com.github.sirdx.plainalarm.presentation.screen.addalarm

import androidx.lifecycle.ViewModel
import com.github.sirdx.plainalarm.domain.usecase.alarm.UpsertAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAlarmViewModel @Inject constructor(
    private val upsertAlarmUseCase: UpsertAlarmUseCase
) : ViewModel() {


}
