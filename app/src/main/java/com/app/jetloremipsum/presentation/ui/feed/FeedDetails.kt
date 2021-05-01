package com.app.jetloremipsum.presentation.ui.feed

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable

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








