package id.tiooooo.pokedata.ui.pages.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import id.tiooooo.pokedata.R
import id.tiooooo.pokedata.ui.component.bottomNavigation.BottomNavModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardScreenModel : ScreenModel {
    val bottomNavList = MutableStateFlow(
        listOf(
            BottomNavModel(
                iconNotSelected = R.drawable.ic_menu_home_not_selected,
                iconSelected = R.drawable.ic_menu_home_selected,
                label = "Home",
                slug = BottomNavTarget.HOME,
            ),
            BottomNavModel(
                iconNotSelected = R.drawable.ic_menu_profile_not_selected,
                iconSelected = R.drawable.ic_menu_profile_selected,
                label = "Profile",
                slug = BottomNavTarget.PROFILE,
            ),
        )
    ).asStateFlow()

    private val _bottomSheetPosition = MutableStateFlow(BottomNavTarget.HOME)
    val bottomSheetPosition: StateFlow<String> = _bottomSheetPosition

    fun updateBottomSheetPosition(positionPath: String) {
        _bottomSheetPosition.value = positionPath
    }
}

object BottomNavTarget {
    const val HOME = "home"
    const val PROFILE = "profile"
}