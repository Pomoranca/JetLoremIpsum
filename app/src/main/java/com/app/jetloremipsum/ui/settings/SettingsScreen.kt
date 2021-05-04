package com.app.jetloremipsum.ui.settings

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen(context: Context) {

    val scope = rememberCoroutineScope()

    val volume: Flow<Long> = context.settingsDataStore.data
        .map { settings ->
            settings.volumeCounter
        }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 24.dp)) {

        Text(text = "Settings", style = MaterialTheme.typography.h6)

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {

            Image(imageVector = Icons.Filled.Notifications, contentDescription = null)
            Slider(
                value = volume.collectAsState(initial = 0).value.toFloat(),
                onValueChange = { value ->
                    scope.launch {
                        incrementCounter(context, value.toLong())
                    }
                },
                valueRange = 0f..100f,
                modifier = Modifier
                    .padding(start = 24.dp)
                    .fillMaxWidth())

        }
    }

}


suspend fun incrementCounter(context: Context, value: Long) {
    context.settingsDataStore.updateData { currentSettings ->
        currentSettings.toBuilder()
            .setVolumeCounter(value)
            .build()
    }
}

