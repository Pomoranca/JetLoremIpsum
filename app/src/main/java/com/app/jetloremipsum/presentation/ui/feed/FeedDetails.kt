package com.app.jetloremipsum.presentation.ui.feed

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.jetloremipsum.result.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import kotlin.random.Random

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FeedDetailsScreen(
    feedId: Int?,
    activity: ComponentActivity,
    loading: Boolean
) {
    val feedDetailsViewModel by activity.viewModels<FeedDetailsViewModel>()


    //TODO SHOW DETAILS SCREEN

    }








