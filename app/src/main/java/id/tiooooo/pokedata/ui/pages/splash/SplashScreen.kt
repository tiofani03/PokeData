package id.tiooooo.pokedata.ui.pages.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.R
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.component.AnimatedPreloader
import id.tiooooo.pokedata.ui.pages.dashboard.DashboardRoute
import id.tiooooo.pokedata.ui.pages.login.LoginRoute
import id.tiooooo.pokedata.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.ui.theme.textMedium12
import id.tiooooo.pokedata.ui.theme.textMedium20
import id.tiooooo.pokedata.utils.AppConstants

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    screenModel: SplashScreenModel
) {
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        screenModel.dispatch(SplashIntent.CheckLogin)
        screenModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.NavigateToHome -> navigator.replace(DashboardRoute())
                SplashEffect.NavigateToLogin -> navigator.replace(LoginRoute())
            }
        }
    }

    BaseScaffold { paddingValues ->
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(bottom = MEDIUM_PADDING),
            ) {
                AnimatedPreloader(
                    modifier = Modifier
                        .size(225.dp)
                        .align(Alignment.CenterHorizontally),
                    animationRes = R.raw.pika_run,
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(
                            vertical = SMALL_PADDING,
                            horizontal = EXTRA_LARGE_PADDING,
                        )
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.app_name),
                    style = textMedium20().copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = EXTRA_LARGE_PADDING)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.text_splash_subtitle),
                    style = textMedium12().copy(
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                    )
                )
            }

            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = paddingValues.calculateBottomPadding() + MEDIUM_PADDING)
                    .align(Alignment.BottomCenter),
                text = "Versi ${AppConstants.APP_VERSION}",
                style = textMedium12().copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                )
            )
        }
    }
}