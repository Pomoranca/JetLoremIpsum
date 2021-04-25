# JetLoremIpsum [UNDER DEVELOPMENT]

JetLoremIpsum is a sample no fragment, single activity app, built with
[Jetpack Compose](https://developer.android.com/jetpack/compose). The goal of the sample is to
showcase capabilities of Compose.

To try out this app, you need to use the latest Canary version of Android Studio 4.2.
You can clone this repository or import the
project from Android Studio.

![image](https://user-images.githubusercontent.com/48402104/115151034-6c121000-a06b-11eb-9573-cc18e80c2f85.png)

## Features

This sample contains several screens: a welcome screen, where the user can enter their email, sign in and sign up screens and main screen showing how to use navigation with compose.


### Sign in/sign up

This package contains 3 screens:
* Welcome
* Sign in
* Sign up

To get to the sign up screen, enter an email that contains "signup".
These screens show how to create different custom composable functions, reused them across multiple screens and handle UI state.

See how to:

* Use `TextField`s
* Implement `TextField` validation across one `TextField` (e.g. email validation) and across multiple `TextFields` (e.g. password confirmation)
* Use a `Snackbar`
* Use different types of `Button`s: `TextButton`, `OutlinedButton` and `Button`

### Navigate in main screen

Use of Retrofit and Jetpack Compose navigation, combined with Bottom navigation, ViewModel, CoilImage, LazyColumn and pagination.


### Data

The data in the sample is from 
https://jsonplaceholder.typicode.com/

