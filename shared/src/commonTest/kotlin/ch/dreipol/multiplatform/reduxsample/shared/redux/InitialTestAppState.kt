package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

val initialTestAppState: AppState
    get() = AppState(
        appLanguage = AppLanguage.GERMAN,
        navigationState = NavigationState(listOf(MainScreen.CALENDAR), NavigationDirection.PUSH),
    )