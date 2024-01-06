package com.github.sirdx.plainalarm.domain.di

import com.github.sirdx.plainalarm.domain.repository.AlarmRepository
import com.github.sirdx.plainalarm.domain.usecase.alarm.DeleteAlarmUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.GetAlarmByIdUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.GetAllAlarmsUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.ToggleAlarmUseCase
import com.github.sirdx.plainalarm.domain.usecase.alarm.UpsertAlarmUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainUseCaseModule {

    @Provides
    fun provideUpsertAlarmUseCase(
        alarmRepository: AlarmRepository
    ) = UpsertAlarmUseCase(alarmRepository)

    @Provides
    fun provideDeleteAlarmUseCase(
        alarmRepository: AlarmRepository
    ) = DeleteAlarmUseCase(alarmRepository)

    @Provides
    fun provideGetAllAlarmsUseCase(
        alarmRepository: AlarmRepository
    ) = GetAllAlarmsUseCase(alarmRepository)

    @Provides
    fun provideGetAlarmByIdUseCase(
        alarmRepository: AlarmRepository
    ) = GetAlarmByIdUseCase(alarmRepository)

    @Provides
    fun provideToggleAlarmUseCase(
        alarmRepository: AlarmRepository
    ) = ToggleAlarmUseCase(alarmRepository)
}
