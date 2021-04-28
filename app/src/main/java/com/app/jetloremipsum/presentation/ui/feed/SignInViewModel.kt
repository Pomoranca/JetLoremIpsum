package com.app.jetloremipsum.presentation.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.jetloremipsum.presentation.ui.Screen
import com.app.jetloremipsum.presentation.ui.welcome.SignInEvent
import com.app.jetloremipsum.presentation.ui.welcome.SignInEvent.*
import com.app.jetloremipsum.presentation.ui.welcome.UserRepository
import com.app.jetloremipsum.presentation.util.Event
import com.app.jetloremipsum.repository.impl.PostsRepository

class SignInViewModel(private val userRepository: UserRepository) : ViewModel(){


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
