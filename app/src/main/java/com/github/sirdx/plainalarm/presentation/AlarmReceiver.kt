package com.github.sirdx.plainalarm.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(EXTRA_MESSAGE) ?: return

        context?.let { ctx ->
            // TODO: Display an UI
            Log.i("ALARM", message)
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }
}
