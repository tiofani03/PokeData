package id.tiooooo.pokedata.ui.pages.dashboard

import id.tiooooo.pokedata.base.BaseScreenModel
import id.tiooooo.pokedata.data.api.repository.UserRepository

class DashboardScreenModel(
    private val userRepository: UserRepository,
) : BaseScreenModel<DashboardState, DashboardIntent, DashboardEffect>(
    initialState = DashboardState()
) {
    override fun reducer(state: DashboardState, intent: DashboardIntent): DashboardState {
        return when (intent) {
            is DashboardIntent.ExecuteLogout -> state
        }
    }

    override suspend fun handleIntentSideEffect(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.ExecuteLogout -> {
                userRepository.executeLogout().collect {
                    if (it) sendEffect(DashboardEffect.NavigateToLogin)
                }
            }
        }
    }
}

sealed interface DashboardEffect {
    data object NavigateToLogin : DashboardEffect
}

data class DashboardState(
    val isLoading: Boolean = false,
    val error: String? = null,
)

sealed interface DashboardIntent {
    data object ExecuteLogout : DashboardIntent
}