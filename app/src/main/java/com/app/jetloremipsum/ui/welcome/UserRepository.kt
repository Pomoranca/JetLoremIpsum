package com.app.jetloremipsum.ui.welcome

import androidx.compose.runtime.Immutable
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

sealed class User {
    @Immutable
    data class LoggedInUser(val email: String) : User()
    object GuestUser : User()
    object NoUserLoggedIn : User()
}

/**
 * Repository that holds the logged in user.
 *
 * In a production app, this class would also handle the communication with the backend for
 * sign in and sign up.
 */
@Module
@InstallIn(SingletonComponent::class)
object UserRepository {

    private var _user: User = User.NoUserLoggedIn

    @Suppress("UNUSED_PARAMETER")
    fun signIn(email: String, password: String) {
        _user = User.LoggedInUser(email)
    }

    fun signInAsGuest() {
        _user = User.GuestUser
    }

}
