package id.tiooooo.pokedata.ui.pages.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.pages.dashboard.component.BottomNavDashboard
import id.tiooooo.pokedata.ui.pages.home.HomeScreen
import id.tiooooo.pokedata.ui.pages.profile.ProfileScreen
import id.tiooooo.pokedata.ui.pages.profile.ProfileScreenModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    screenModel: DashboardScreenModel,
    profileScreenModel: ProfileScreenModel,
) {
    val bottomNavList by screenModel.bottomNavList.collectAsState()
    val bottomSelectedItem by screenModel.bottomSheetPosition.collectAsState()

    BaseScaffold(
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when (bottomSelectedItem) {
                    BottomNavTarget.HOME -> {
                        HomeScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                        )
                    }

                    BottomNavTarget.PROFILE -> {
                        ProfileScreen(
                            modifier = Modifier
                                .fillMaxSize(),
                            screenModel = profileScreenModel,
                        )
                    }
                }
            }
            BottomNavDashboard(
                modifier = Modifier
                    .fillMaxWidth(),
                bottomNavList = bottomNavList,
                bottomSelectedItem = bottomSelectedItem,
                onBottomNavClicked = {
                    screenModel.updateBottomSheetPosition(it)
                }
            )
        }
    }
}