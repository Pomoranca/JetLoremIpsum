# JetLoremIpsum

`JETPACK COMPOSE` - `NAVIGATION` - `DATASTORE` - `VIEWMODEL` - `COROUTINES` - `FLOW` - `PAGINATION` - `LIVEDATA` 

JetLoremIpsum is a sample no fragment, single activity app, built with
[Jetpack Compose](https://developer.android.com/jetpack/compose). The goal of the sample is to
showcase capabilities of Compose.

To try out this app, you need to use the latest Canary version of Android Studio 4.2.
You can clone this repository or import the
project from Android Studio.

![image](https://user-images.githubusercontent.com/48402104/117341404-593c6f80-aea2-11eb-8101-dac099def526.png)

## Features

This sample contains several screens: a welcome screen, where the user can enter their email, sign in and screen and main screen showing how to use tab navigation with compose. 

### Sign in/sign up

See how to:

* Use `TextField`s
* Implement `TextField` validation across one `TextField` (e.g. email validation) and across multiple `TextFields` (e.g. password confirmation)
* Use a `Snackbar`
* Use different types of `Button`s: `TextButton`, `OutlinedButton` and `Button`

### Navigate in main screen

Use of Retrofit and `Jetpack Compose navigation`, combined with` Tab Layout`, `ViewModel`, `CoilImage`, `LazyColumn`, `Lottie` and `Pagination`.
Loading is purposly delayed to show use of Lottie loading animation.

### Notifications/Favorites

Use of `Lottie` animation 

### Settings

This composable screen shows how to use `Jetpack Datastore`, more precisely `Proto DataStore`.
DataStore is a new and improved data storage solution aimed at replacing SharedPreferences. Built on Kotlin coroutines and Flow, DataStore provides two different implementations: Proto DataStore, that lets you store typed objects (backed by protocol buffers) and Preferences DataStore, that stores key-value pairs. Data is stored asynchronously, consistently, and transactionally, overcoming some of the drawbacks of SharedPreferences.

### Data

The data in the sample is from 
https://jsonplaceholder.typicode.com/

