package com.github.sirdx.plainalarm.data.repository.di

import com.github.sirdx.plainalarm.data.repository.AlarmRepositoryImpl
import com.github.sirdx.plainalarm.data.source.local.db.dao.AlarmDao
import com.github.sirdx.plainalarm.data.source.local.scheduler.AlarmScheduler
import com.github.sirdx.plainalarm.domain.repository.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideAlarmRepositoryImpl(
        alarmDao: AlarmDao,
        alarmScheduler: AlarmScheduler
    ): AlarmRepository = AlarmRepositoryImpl(alarmDao, alarmScheduler)
}
