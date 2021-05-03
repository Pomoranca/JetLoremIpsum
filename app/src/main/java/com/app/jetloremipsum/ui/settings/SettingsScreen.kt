package com.app.jetloremipsum.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.jetloremipsum.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait


@Composable
fun SettingsScreen(context: Context) {

    val scope = rememberCoroutineScope()

    val volume: Flow<Int> = context.settingsDataStore.data
        .map { settings ->
            settings.volumeCounter
        }

    Column {
        Slider(value = volume.collectAsState(initial = 0).value.toFloat(),
            onValueChange = { value ->
                scope.launch {
                    incrementCounter(context, value.toInt())
                }
            },
            valueRange = 0f..100f,
            modifier = Modifier.padding(24.dp))
    }
}


suspend fun incrementCounter(context: Context, value: Int) {
    context.settingsDataStore.updateData { currentSettings ->
        currentSettings.toBuilder()
            .setVolumeCounter(value)
            .build()
    }
}

