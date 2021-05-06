package com.app.jetloremipsum.ui.feed

import androidx.lifecycle.ViewModel
import com.app.jetloremipsum.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FeedDetailsViewModel @Inject constructor(
    val repository: PostsRepository,
) : ViewModel()


