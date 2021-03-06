package com.app.jetloremipsum.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.jetloremipsum.navigation.Screen
import com.app.jetloremipsum.repository.PostsRepository
import com.app.jetloremipsum.ui.welcome.UserRepository
import com.app.jetloremipsum.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    /**
     * Consider all sign ins successful
     */

    fun signIn(email: String, password: String) {
        userRepository.signIn(email, password)
        _navigateTo.value = Event(Screen.Feed)
    }

    fun signInAsGuest() {
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Screen.Feed)
    }

    fun signUp() {
//        _navigateTo.value = Event(SignUp)
    }
}

@Suppress("UNCHECKED_CAST")
class SignInViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
