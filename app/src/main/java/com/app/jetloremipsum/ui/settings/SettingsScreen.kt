package com.app.jetloremipsum.ui.settings

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Countertops
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.app.jetloremipsum.R
import com.app.jetloremipsum.ui.welcome.EmailState
import com.app.jetloremipsum.ui.welcome.TextFieldError
import com.app.jetloremipsum.ui.welcome.TextFieldState
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

        CustomDivider()

        //edit notifications volume
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

        CustomDivider()

        //edit user name
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {

            Image(imageVector = Icons.Filled.VerifiedUser, contentDescription = null)


        }

        CustomDivider()

        //edit some number
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {

            Image(imageVector = Icons.Filled.Countertops, contentDescription = null)
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


@Composable
fun CustomTextField(
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},

) {
    OutlinedTextField(
        value = emailState.text,
        onValueChange = {
            emailState.text = it
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.body2
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                emailState.onFocusChange(focused)
                if (!focused) {
                    emailState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        )
    )

    emailState.getError()?.let { error -> TextFieldError(textError = error) }
}

@Composable
fun CustomDivider(){
    Divider(color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp))
}


suspend fun incrementCounter(context: Context, value: Long) {
    context.settingsDataStore.updateData { currentSettings ->
        currentSettings.toBuilder()
            .setVolumeCounter(value)
            .build()
    }
}

