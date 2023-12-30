package com.github.sirdx.plainalarm.data.source.local.scheduler.di

import android.content.Context
import com.github.sirdx.plainalarm.data.source.local.scheduler.AlarmScheduler
import com.github.sirdx.plainalarm.data.source.local.scheduler.AlarmSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalSchedulerModule {

    @Provides
    fun provideAlarmSchedulerImpl(
        @ApplicationContext context: Context
    ): AlarmScheduler = AlarmSchedulerImpl(context)
}
