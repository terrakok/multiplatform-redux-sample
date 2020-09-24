package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.presenterEnhancer
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.coroutineMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.loggerMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.onboardingMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.syncDisposalsMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import org.reduxkotlin.*

class ReduxSampleApp {
    val store = createThreadSafeStore(
        rootReducer,
        AppState.INITIAL_STATE,
        compose(
            listOf(
                presenterEnhancer(uiDispatcher),
                applyMiddleware(
                    coroutineMiddleware(uiDispatcher),
                    loggerMiddleware(),
                    createThunkMiddleware(),
                    syncDisposalsMiddleware(),
                    onboardingMiddleware(),
                ),
            )
        )
    )
    init {
        // TODO check if Onboarding should be shown
        store.dispatch(NavigationAction.ONBOARDING_START)
    }
}