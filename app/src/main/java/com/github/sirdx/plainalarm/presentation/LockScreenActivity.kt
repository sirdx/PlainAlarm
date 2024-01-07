package com.github.sirdx.plainalarm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.sirdx.plainalarm.core.turnScreenOffAndKeyguardOn
import com.github.sirdx.plainalarm.core.turnScreenOnAndKeyguardOff
import com.github.sirdx.plainalarm.presentation.ui.theme.PlainAlarmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmName = intent.getStringExtra(EXTRA_NAME) ?: "null"

        setContent {
            PlainAlarmTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Alarm",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Alarm",
                            style = MaterialTheme.typography.displayLarge
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = alarmName,
                            style = MaterialTheme.typography.displaySmall
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        OutlinedButton(onClick = {
                            this@LockScreenActivity.finish()
                        }) {
                            Text(text = "Close")
                        }
                    }
                }
            }
        }
        turnScreenOnAndKeyguardOff()
    }

    override fun onDestroy() {
        super.onDestroy()
        turnScreenOffAndKeyguardOn()
    }

    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
    }
}
