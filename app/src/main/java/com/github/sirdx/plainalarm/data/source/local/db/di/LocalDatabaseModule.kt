package com.github.sirdx.plainalarm.data.source.local.db.di

import android.content.Context
import androidx.room.Room
import com.github.sirdx.plainalarm.data.source.local.db.PlainAlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalDatabaseModule {

    @Provides
    fun providePlainAlarmDatabase(@ApplicationContext context: Context): PlainAlarmDatabase =
        Room.databaseBuilder(
            context,
            PlainAlarmDatabase::class.java,
            "plainalarm.db"
        ).build()

    @Provides
    fun provideAlarmDao(plainAlarmDatabase: PlainAlarmDatabase) =
        plainAlarmDatabase.alarmDao()
}
